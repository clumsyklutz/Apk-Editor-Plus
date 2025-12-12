package android.support.graphics.drawable

import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.Region
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.content.res.ComplexColorCompat
import androidx.core.content.res.ResourcesCompat
import android.support.v4.content.res.TypedArrayUtils
import android.support.v4.graphics.PathParser
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.util.ArrayMap
import android.util.AttributeSet
import android.util.Log
import android.util.Xml
import jadx.core.codegen.CodeWriter
import java.io.IOException
import java.util.ArrayDeque
import java.util.ArrayList
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

class VectorDrawableCompat extends VectorDrawableCommon {
    private static val DBG_VECTOR_DRAWABLE = false
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN
    private static val LINECAP_BUTT = 0
    private static val LINECAP_ROUND = 1
    private static val LINECAP_SQUARE = 2
    private static val LINEJOIN_BEVEL = 2
    private static val LINEJOIN_MITER = 0
    private static val LINEJOIN_ROUND = 1
    static val LOGTAG = "VectorDrawableCompat"
    private static val MAX_CACHED_BITMAP_SIZE = 2048
    private static val SHAPE_CLIP_PATH = "clip-path"
    private static val SHAPE_GROUP = "group"
    private static val SHAPE_PATH = "path"
    private static val SHAPE_VECTOR = "vector"
    private Boolean mAllowCaching
    private Drawable.ConstantState mCachedConstantStateDelegate
    private ColorFilter mColorFilter
    private Boolean mMutated
    private PorterDuffColorFilter mTintFilter
    private final Rect mTmpBounds
    private final Array<Float> mTmpFloats
    private final Matrix mTmpMatrix
    private VectorDrawableCompatState mVectorState

    class VClipPath extends VPath {
        constructor() {
        }

        constructor(VClipPath vClipPath) {
            super(vClipPath)
        }

        private fun updateStateFromTypedArray(TypedArray typedArray) {
            String string = typedArray.getString(0)
            if (string != null) {
                this.mPathName = string
            }
            String string2 = typedArray.getString(1)
            if (string2 != null) {
                this.mNodes = PathParser.createNodesFromPathData(string2)
            }
        }

        fun inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH)
                updateStateFromTypedArray(typedArrayObtainAttributes)
                typedArrayObtainAttributes.recycle()
            }
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VPath
        fun isClipPath() {
            return true
        }
    }

    class VFullPath extends VPath {
        private static val FILL_TYPE_WINDING = 0
        Float mFillAlpha
        ComplexColorCompat mFillColor
        Int mFillRule
        Float mStrokeAlpha
        ComplexColorCompat mStrokeColor
        Paint.Cap mStrokeLineCap
        Paint.Join mStrokeLineJoin
        Float mStrokeMiterlimit
        Float mStrokeWidth
        private Array<Int> mThemeAttrs
        Float mTrimPathEnd
        Float mTrimPathOffset
        Float mTrimPathStart

        constructor() {
            this.mStrokeWidth = 0.0f
            this.mStrokeAlpha = 1.0f
            this.mFillRule = 0
            this.mFillAlpha = 1.0f
            this.mTrimPathStart = 0.0f
            this.mTrimPathEnd = 1.0f
            this.mTrimPathOffset = 0.0f
            this.mStrokeLineCap = Paint.Cap.BUTT
            this.mStrokeLineJoin = Paint.Join.MITER
            this.mStrokeMiterlimit = 4.0f
        }

        constructor(VFullPath vFullPath) {
            super(vFullPath)
            this.mStrokeWidth = 0.0f
            this.mStrokeAlpha = 1.0f
            this.mFillRule = 0
            this.mFillAlpha = 1.0f
            this.mTrimPathStart = 0.0f
            this.mTrimPathEnd = 1.0f
            this.mTrimPathOffset = 0.0f
            this.mStrokeLineCap = Paint.Cap.BUTT
            this.mStrokeLineJoin = Paint.Join.MITER
            this.mStrokeMiterlimit = 4.0f
            this.mThemeAttrs = vFullPath.mThemeAttrs
            this.mStrokeColor = vFullPath.mStrokeColor
            this.mStrokeWidth = vFullPath.mStrokeWidth
            this.mStrokeAlpha = vFullPath.mStrokeAlpha
            this.mFillColor = vFullPath.mFillColor
            this.mFillRule = vFullPath.mFillRule
            this.mFillAlpha = vFullPath.mFillAlpha
            this.mTrimPathStart = vFullPath.mTrimPathStart
            this.mTrimPathEnd = vFullPath.mTrimPathEnd
            this.mTrimPathOffset = vFullPath.mTrimPathOffset
            this.mStrokeLineCap = vFullPath.mStrokeLineCap
            this.mStrokeLineJoin = vFullPath.mStrokeLineJoin
            this.mStrokeMiterlimit = vFullPath.mStrokeMiterlimit
        }

        private Paint.Cap getStrokeLineCap(Int i, Paint.Cap cap) {
            switch (i) {
                case 0:
                    return Paint.Cap.BUTT
                case 1:
                    return Paint.Cap.ROUND
                case 2:
                    return Paint.Cap.SQUARE
                default:
                    return cap
            }
        }

        private Paint.Join getStrokeLineJoin(Int i, Paint.Join join) {
            switch (i) {
                case 0:
                    return Paint.Join.MITER
                case 1:
                    return Paint.Join.ROUND
                case 2:
                    return Paint.Join.BEVEL
                default:
                    return join
            }
        }

        private fun updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.mThemeAttrs = null
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0)
                if (string != null) {
                    this.mPathName = string
                }
                String string2 = typedArray.getString(2)
                if (string2 != null) {
                    this.mNodes = PathParser.createNodesFromPathData(string2)
                }
                this.mFillColor = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "fillColor", 1, 0)
                this.mFillAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.mFillAlpha)
                this.mStrokeLineCap = getStrokeLineCap(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.mStrokeLineCap)
                this.mStrokeLineJoin = getStrokeLineJoin(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.mStrokeLineJoin)
                this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.mStrokeMiterlimit)
                this.mStrokeColor = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "strokeColor", 3, 0)
                this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.mStrokeAlpha)
                this.mStrokeWidth = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.mStrokeWidth)
                this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.mTrimPathEnd)
                this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.mTrimPathOffset)
                this.mTrimPathStart = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.mTrimPathStart)
                this.mFillRule = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.mFillRule)
            }
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VPath
        fun applyTheme(Resources.Theme theme) {
            if (this.mThemeAttrs == null) {
            }
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VPath
        fun canApplyTheme() {
            return this.mThemeAttrs != null
        }

        Float getFillAlpha() {
            return this.mFillAlpha
        }

        @ColorInt
        Int getFillColor() {
            return this.mFillColor.getColor()
        }

        Float getStrokeAlpha() {
            return this.mStrokeAlpha
        }

        @ColorInt
        Int getStrokeColor() {
            return this.mStrokeColor.getColor()
        }

        Float getStrokeWidth() {
            return this.mStrokeWidth
        }

        Float getTrimPathEnd() {
            return this.mTrimPathEnd
        }

        Float getTrimPathOffset() {
            return this.mTrimPathOffset
        }

        Float getTrimPathStart() {
            return this.mTrimPathStart
        }

        fun inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH)
            updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser, theme)
            typedArrayObtainAttributes.recycle()
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VObject
        fun isStateful() {
            return this.mFillColor.isStateful() || this.mStrokeColor.isStateful()
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VObject
        fun onStateChanged(Array<Int> iArr) {
            return this.mFillColor.onStateChanged(iArr) | this.mStrokeColor.onStateChanged(iArr)
        }

        Unit setFillAlpha(Float f) {
            this.mFillAlpha = f
        }

        Unit setFillColor(Int i) {
            this.mFillColor.setColor(i)
        }

        Unit setStrokeAlpha(Float f) {
            this.mStrokeAlpha = f
        }

        Unit setStrokeColor(Int i) {
            this.mStrokeColor.setColor(i)
        }

        Unit setStrokeWidth(Float f) {
            this.mStrokeWidth = f
        }

        Unit setTrimPathEnd(Float f) {
            this.mTrimPathEnd = f
        }

        Unit setTrimPathOffset(Float f) {
            this.mTrimPathOffset = f
        }

        Unit setTrimPathStart(Float f) {
            this.mTrimPathStart = f
        }
    }

    class VGroup extends VObject {
        Int mChangingConfigurations
        final ArrayList mChildren
        private String mGroupName
        final Matrix mLocalMatrix
        private Float mPivotX
        private Float mPivotY
        Float mRotate
        private Float mScaleX
        private Float mScaleY
        final Matrix mStackedMatrix
        private Array<Int> mThemeAttrs
        private Float mTranslateX
        private Float mTranslateY

        constructor() {
            super()
            this.mStackedMatrix = Matrix()
            this.mChildren = ArrayList()
            this.mRotate = 0.0f
            this.mPivotX = 0.0f
            this.mPivotY = 0.0f
            this.mScaleX = 1.0f
            this.mScaleY = 1.0f
            this.mTranslateX = 0.0f
            this.mTranslateY = 0.0f
            this.mLocalMatrix = Matrix()
            this.mGroupName = null
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v8, types: [android.support.graphics.drawable.VectorDrawableCompat$VFullPath] */
        /* JADX WARN: Type inference failed for: r7v0, types: [android.support.v4.util.ArrayMap] */
        constructor(VGroup vGroup, ArrayMap arrayMap) {
            VClipPath vClipPath
            super()
            this.mStackedMatrix = Matrix()
            this.mChildren = ArrayList()
            this.mRotate = 0.0f
            this.mPivotX = 0.0f
            this.mPivotY = 0.0f
            this.mScaleX = 1.0f
            this.mScaleY = 1.0f
            this.mTranslateX = 0.0f
            this.mTranslateY = 0.0f
            this.mLocalMatrix = Matrix()
            this.mGroupName = null
            this.mRotate = vGroup.mRotate
            this.mPivotX = vGroup.mPivotX
            this.mPivotY = vGroup.mPivotY
            this.mScaleX = vGroup.mScaleX
            this.mScaleY = vGroup.mScaleY
            this.mTranslateX = vGroup.mTranslateX
            this.mTranslateY = vGroup.mTranslateY
            this.mThemeAttrs = vGroup.mThemeAttrs
            this.mGroupName = vGroup.mGroupName
            this.mChangingConfigurations = vGroup.mChangingConfigurations
            if (this.mGroupName != null) {
                arrayMap.put(this.mGroupName, this)
            }
            this.mLocalMatrix.set(vGroup.mLocalMatrix)
            ArrayList arrayList = vGroup.mChildren
            Int i = 0
            while (true) {
                Int i2 = i
                if (i2 >= arrayList.size()) {
                    return
                }
                Object obj = arrayList.get(i2)
                if (obj is VGroup) {
                    this.mChildren.add(VGroup((VGroup) obj, arrayMap))
                } else {
                    if (obj is VFullPath) {
                        vClipPath = VFullPath((VFullPath) obj)
                    } else {
                        if (!(obj is VClipPath)) {
                            throw IllegalStateException("Unknown object in the tree!")
                        }
                        vClipPath = VClipPath((VClipPath) obj)
                    }
                    this.mChildren.add(vClipPath)
                    if (vClipPath.mPathName != null) {
                        arrayMap.put(vClipPath.mPathName, vClipPath)
                    }
                }
                i = i2 + 1
            }
        }

        private fun updateLocalMatrix() {
            this.mLocalMatrix.reset()
            this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY)
            this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY)
            this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f)
            this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY)
        }

        private fun updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.mThemeAttrs = null
            this.mRotate = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "rotation", 5, this.mRotate)
            this.mPivotX = typedArray.getFloat(1, this.mPivotX)
            this.mPivotY = typedArray.getFloat(2, this.mPivotY)
            this.mScaleX = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleX", 3, this.mScaleX)
            this.mScaleY = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleY", 4, this.mScaleY)
            this.mTranslateX = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateX", 6, this.mTranslateX)
            this.mTranslateY = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateY", 7, this.mTranslateY)
            String string = typedArray.getString(0)
            if (string != null) {
                this.mGroupName = string
            }
            updateLocalMatrix()
        }

        fun getGroupName() {
            return this.mGroupName
        }

        fun getLocalMatrix() {
            return this.mLocalMatrix
        }

        fun getPivotX() {
            return this.mPivotX
        }

        fun getPivotY() {
            return this.mPivotY
        }

        fun getRotation() {
            return this.mRotate
        }

        fun getScaleX() {
            return this.mScaleX
        }

        fun getScaleY() {
            return this.mScaleY
        }

        fun getTranslateX() {
            return this.mTranslateX
        }

        fun getTranslateY() {
            return this.mTranslateY
        }

        fun inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP)
            updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser)
            typedArrayObtainAttributes.recycle()
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VObject
        fun isStateful() {
            for (Int i = 0; i < this.mChildren.size(); i++) {
                if (((VObject) this.mChildren.get(i)).isStateful()) {
                    return true
                }
            }
            return false
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.VObject
        fun onStateChanged(Array<Int> iArr) {
            Boolean zOnStateChanged = false
            for (Int i = 0; i < this.mChildren.size(); i++) {
                zOnStateChanged |= ((VObject) this.mChildren.get(i)).onStateChanged(iArr)
            }
            return zOnStateChanged
        }

        fun setPivotX(Float f) {
            if (f != this.mPivotX) {
                this.mPivotX = f
                updateLocalMatrix()
            }
        }

        fun setPivotY(Float f) {
            if (f != this.mPivotY) {
                this.mPivotY = f
                updateLocalMatrix()
            }
        }

        fun setRotation(Float f) {
            if (f != this.mRotate) {
                this.mRotate = f
                updateLocalMatrix()
            }
        }

        fun setScaleX(Float f) {
            if (f != this.mScaleX) {
                this.mScaleX = f
                updateLocalMatrix()
            }
        }

        fun setScaleY(Float f) {
            if (f != this.mScaleY) {
                this.mScaleY = f
                updateLocalMatrix()
            }
        }

        fun setTranslateX(Float f) {
            if (f != this.mTranslateX) {
                this.mTranslateX = f
                updateLocalMatrix()
            }
        }

        fun setTranslateY(Float f) {
            if (f != this.mTranslateY) {
                this.mTranslateY = f
                updateLocalMatrix()
            }
        }
    }

    abstract class VObject {
        private constructor() {
        }

        fun isStateful() {
            return false
        }

        fun onStateChanged(Array<Int> iArr) {
            return false
        }
    }

    abstract class VPath extends VObject {
        Int mChangingConfigurations
        protected PathParser.Array<PathDataNode> mNodes
        String mPathName

        constructor() {
            super()
            this.mNodes = null
        }

        constructor(VPath vPath) {
            super()
            this.mNodes = null
            this.mPathName = vPath.mPathName
            this.mChangingConfigurations = vPath.mChangingConfigurations
            this.mNodes = PathParser.deepCopyNodes(vPath.mNodes)
        }

        fun applyTheme(Resources.Theme theme) {
        }

        fun canApplyTheme() {
            return false
        }

        public PathParser.Array<PathDataNode> getPathData() {
            return this.mNodes
        }

        fun getPathName() {
            return this.mPathName
        }

        fun isClipPath() {
            return false
        }

        fun nodesToString(PathParser.Array<PathDataNode> pathDataNodeArr) {
            String str = " "
            for (Int i = 0; i < pathDataNodeArr.length; i++) {
                String str2 = str + pathDataNodeArr[i].mType + ":"
                str = str2
                for (Float f : pathDataNodeArr[i].mParams) {
                    str = str + f + ","
                }
            }
            return str
        }

        fun printVPath(Int i) {
            String str = ""
            for (Int i2 = 0; i2 < i; i2++) {
                str = str + CodeWriter.INDENT
            }
            Log.v(VectorDrawableCompat.LOGTAG, str + "current path is :" + this.mPathName + " pathData is " + nodesToString(this.mNodes))
        }

        fun setPathData(PathParser.Array<PathDataNode> pathDataNodeArr) {
            if (PathParser.canMorph(this.mNodes, pathDataNodeArr)) {
                PathParser.updateNodes(this.mNodes, pathDataNodeArr)
            } else {
                this.mNodes = PathParser.deepCopyNodes(pathDataNodeArr)
            }
        }

        fun toPath(Path path) {
            path.reset()
            if (this.mNodes != null) {
                PathParser.PathDataNode.nodesToPath(this.mNodes, path)
            }
        }
    }

    class VPathRenderer {
        private static val IDENTITY_MATRIX = Matrix()
        Float mBaseHeight
        Float mBaseWidth
        private Int mChangingConfigurations
        Paint mFillPaint
        private final Matrix mFinalPathMatrix
        Boolean mIsStateful
        private final Path mPath
        private PathMeasure mPathMeasure
        private final Path mRenderPath
        Int mRootAlpha
        final VGroup mRootGroup
        String mRootName
        Paint mStrokePaint
        final ArrayMap mVGTargetsMap
        Float mViewportHeight
        Float mViewportWidth

        constructor() {
            this.mFinalPathMatrix = Matrix()
            this.mBaseWidth = 0.0f
            this.mBaseHeight = 0.0f
            this.mViewportWidth = 0.0f
            this.mViewportHeight = 0.0f
            this.mRootAlpha = 255
            this.mRootName = null
            this.mIsStateful = null
            this.mVGTargetsMap = ArrayMap()
            this.mRootGroup = VGroup()
            this.mPath = Path()
            this.mRenderPath = Path()
        }

        constructor(VPathRenderer vPathRenderer) {
            this.mFinalPathMatrix = Matrix()
            this.mBaseWidth = 0.0f
            this.mBaseHeight = 0.0f
            this.mViewportWidth = 0.0f
            this.mViewportHeight = 0.0f
            this.mRootAlpha = 255
            this.mRootName = null
            this.mIsStateful = null
            this.mVGTargetsMap = ArrayMap()
            this.mRootGroup = VGroup(vPathRenderer.mRootGroup, this.mVGTargetsMap)
            this.mPath = Path(vPathRenderer.mPath)
            this.mRenderPath = Path(vPathRenderer.mRenderPath)
            this.mBaseWidth = vPathRenderer.mBaseWidth
            this.mBaseHeight = vPathRenderer.mBaseHeight
            this.mViewportWidth = vPathRenderer.mViewportWidth
            this.mViewportHeight = vPathRenderer.mViewportHeight
            this.mChangingConfigurations = vPathRenderer.mChangingConfigurations
            this.mRootAlpha = vPathRenderer.mRootAlpha
            this.mRootName = vPathRenderer.mRootName
            if (vPathRenderer.mRootName != null) {
                this.mVGTargetsMap.put(vPathRenderer.mRootName, this)
            }
            this.mIsStateful = vPathRenderer.mIsStateful
        }

        private fun cross(Float f, Float f2, Float f3, Float f4) {
            return (f * f4) - (f2 * f3)
        }

        private fun drawGroupTree(VGroup vGroup, Matrix matrix, Canvas canvas, Int i, Int i2, ColorFilter colorFilter) {
            vGroup.mStackedMatrix.set(matrix)
            vGroup.mStackedMatrix.preConcat(vGroup.mLocalMatrix)
            canvas.save()
            Int i3 = 0
            while (true) {
                Int i4 = i3
                if (i4 >= vGroup.mChildren.size()) {
                    canvas.restore()
                    return
                }
                VObject vObject = (VObject) vGroup.mChildren.get(i4)
                if (vObject is VGroup) {
                    drawGroupTree((VGroup) vObject, vGroup.mStackedMatrix, canvas, i, i2, colorFilter)
                } else if (vObject is VPath) {
                    drawPath(vGroup, (VPath) vObject, canvas, i, i2, colorFilter)
                }
                i3 = i4 + 1
            }
        }

        private fun drawPath(VGroup vGroup, VPath vPath, Canvas canvas, Int i, Int i2, ColorFilter colorFilter) {
            Float f = i / this.mViewportWidth
            Float f2 = i2 / this.mViewportHeight
            Float fMin = Math.min(f, f2)
            Matrix matrix = vGroup.mStackedMatrix
            this.mFinalPathMatrix.set(matrix)
            this.mFinalPathMatrix.postScale(f, f2)
            Float matrixScale = getMatrixScale(matrix)
            if (matrixScale == 0.0f) {
                return
            }
            vPath.toPath(this.mPath)
            Path path = this.mPath
            this.mRenderPath.reset()
            if (vPath.isClipPath()) {
                this.mRenderPath.addPath(path, this.mFinalPathMatrix)
                canvas.clipPath(this.mRenderPath)
                return
            }
            VFullPath vFullPath = (VFullPath) vPath
            if (vFullPath.mTrimPathStart != 0.0f || vFullPath.mTrimPathEnd != 1.0f) {
                Float f3 = (vFullPath.mTrimPathStart + vFullPath.mTrimPathOffset) % 1.0f
                Float f4 = (vFullPath.mTrimPathEnd + vFullPath.mTrimPathOffset) % 1.0f
                if (this.mPathMeasure == null) {
                    this.mPathMeasure = PathMeasure()
                }
                this.mPathMeasure.setPath(this.mPath, false)
                Float length = this.mPathMeasure.getLength()
                Float f5 = f3 * length
                Float f6 = f4 * length
                path.reset()
                if (f5 > f6) {
                    this.mPathMeasure.getSegment(f5, length, path, true)
                    this.mPathMeasure.getSegment(0.0f, f6, path, true)
                } else {
                    this.mPathMeasure.getSegment(f5, f6, path, true)
                }
                path.rLineTo(0.0f, 0.0f)
            }
            this.mRenderPath.addPath(path, this.mFinalPathMatrix)
            if (vFullPath.mFillColor.willDraw()) {
                ComplexColorCompat complexColorCompat = vFullPath.mFillColor
                if (this.mFillPaint == null) {
                    this.mFillPaint = Paint(1)
                    this.mFillPaint.setStyle(Paint.Style.FILL)
                }
                Paint paint = this.mFillPaint
                if (complexColorCompat.isGradient()) {
                    Shader shader = complexColorCompat.getShader()
                    shader.setLocalMatrix(this.mFinalPathMatrix)
                    paint.setShader(shader)
                    paint.setAlpha(Math.round(vFullPath.mFillAlpha * 255.0f))
                } else {
                    paint.setColor(VectorDrawableCompat.applyAlpha(complexColorCompat.getColor(), vFullPath.mFillAlpha))
                }
                paint.setColorFilter(colorFilter)
                this.mRenderPath.setFillType(vFullPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD)
                canvas.drawPath(this.mRenderPath, paint)
            }
            if (vFullPath.mStrokeColor.willDraw()) {
                ComplexColorCompat complexColorCompat2 = vFullPath.mStrokeColor
                if (this.mStrokePaint == null) {
                    this.mStrokePaint = Paint(1)
                    this.mStrokePaint.setStyle(Paint.Style.STROKE)
                }
                Paint paint2 = this.mStrokePaint
                if (vFullPath.mStrokeLineJoin != null) {
                    paint2.setStrokeJoin(vFullPath.mStrokeLineJoin)
                }
                if (vFullPath.mStrokeLineCap != null) {
                    paint2.setStrokeCap(vFullPath.mStrokeLineCap)
                }
                paint2.setStrokeMiter(vFullPath.mStrokeMiterlimit)
                if (complexColorCompat2.isGradient()) {
                    Shader shader2 = complexColorCompat2.getShader()
                    shader2.setLocalMatrix(this.mFinalPathMatrix)
                    paint2.setShader(shader2)
                    paint2.setAlpha(Math.round(vFullPath.mStrokeAlpha * 255.0f))
                } else {
                    paint2.setColor(VectorDrawableCompat.applyAlpha(complexColorCompat2.getColor(), vFullPath.mStrokeAlpha))
                }
                paint2.setColorFilter(colorFilter)
                paint2.setStrokeWidth(fMin * matrixScale * vFullPath.mStrokeWidth)
                canvas.drawPath(this.mRenderPath, paint2)
            }
        }

        private fun getMatrixScale(Matrix matrix) {
            Array<Float> fArr = {0.0f, 1.0f, 1.0f, 0.0f}
            matrix.mapVectors(fArr)
            Float fHypot = (Float) Math.hypot(fArr[0], fArr[1])
            Float fHypot2 = (Float) Math.hypot(fArr[2], fArr[3])
            Float fCross = cross(fArr[0], fArr[1], fArr[2], fArr[3])
            Float fMax = Math.max(fHypot, fHypot2)
            if (fMax > 0.0f) {
                return Math.abs(fCross) / fMax
            }
            return 0.0f
        }

        fun draw(Canvas canvas, Int i, Int i2, ColorFilter colorFilter) {
            drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, canvas, i, i2, colorFilter)
        }

        fun getAlpha() {
            return getRootAlpha() / 255.0f
        }

        fun getRootAlpha() {
            return this.mRootAlpha
        }

        fun isStateful() {
            if (this.mIsStateful == null) {
                this.mIsStateful = Boolean.valueOf(this.mRootGroup.isStateful())
            }
            return this.mIsStateful.booleanValue()
        }

        fun onStateChanged(Array<Int> iArr) {
            return this.mRootGroup.onStateChanged(iArr)
        }

        fun setAlpha(Float f) {
            setRootAlpha((Int) (255.0f * f))
        }

        fun setRootAlpha(Int i) {
            this.mRootAlpha = i
        }
    }

    class VectorDrawableCompatState extends Drawable.ConstantState {
        Boolean mAutoMirrored
        Boolean mCacheDirty
        Boolean mCachedAutoMirrored
        Bitmap mCachedBitmap
        Int mCachedRootAlpha
        Array<Int> mCachedThemeAttrs
        ColorStateList mCachedTint
        PorterDuff.Mode mCachedTintMode
        Int mChangingConfigurations
        Paint mTempPaint
        ColorStateList mTint
        PorterDuff.Mode mTintMode
        VPathRenderer mVPathRenderer

        constructor() {
            this.mTint = null
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE
            this.mVPathRenderer = VPathRenderer()
        }

        constructor(VectorDrawableCompatState vectorDrawableCompatState) {
            this.mTint = null
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE
            if (vectorDrawableCompatState != null) {
                this.mChangingConfigurations = vectorDrawableCompatState.mChangingConfigurations
                this.mVPathRenderer = VPathRenderer(vectorDrawableCompatState.mVPathRenderer)
                if (vectorDrawableCompatState.mVPathRenderer.mFillPaint != null) {
                    this.mVPathRenderer.mFillPaint = Paint(vectorDrawableCompatState.mVPathRenderer.mFillPaint)
                }
                if (vectorDrawableCompatState.mVPathRenderer.mStrokePaint != null) {
                    this.mVPathRenderer.mStrokePaint = Paint(vectorDrawableCompatState.mVPathRenderer.mStrokePaint)
                }
                this.mTint = vectorDrawableCompatState.mTint
                this.mTintMode = vectorDrawableCompatState.mTintMode
                this.mAutoMirrored = vectorDrawableCompatState.mAutoMirrored
            }
        }

        fun canReuseBitmap(Int i, Int i2) {
            return i == this.mCachedBitmap.getWidth() && i2 == this.mCachedBitmap.getHeight()
        }

        fun canReuseCache() {
            return !this.mCacheDirty && this.mCachedTint == this.mTint && this.mCachedTintMode == this.mTintMode && this.mCachedAutoMirrored == this.mAutoMirrored && this.mCachedRootAlpha == this.mVPathRenderer.getRootAlpha()
        }

        fun createCachedBitmapIfNeeded(Int i, Int i2) {
            if (this.mCachedBitmap == null || !canReuseBitmap(i, i2)) {
                this.mCachedBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888)
                this.mCacheDirty = true
            }
        }

        fun drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.mCachedBitmap, (Rect) null, rect, getPaint(colorFilter))
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun getChangingConfigurations() {
            return this.mChangingConfigurations
        }

        fun getPaint(ColorFilter colorFilter) {
            if (!hasTranslucentRoot() && colorFilter == null) {
                return null
            }
            if (this.mTempPaint == null) {
                this.mTempPaint = Paint()
                this.mTempPaint.setFilterBitmap(true)
            }
            this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha())
            this.mTempPaint.setColorFilter(colorFilter)
            return this.mTempPaint
        }

        fun hasTranslucentRoot() {
            return this.mVPathRenderer.getRootAlpha() < 255
        }

        fun isStateful() {
            return this.mVPathRenderer.isStateful()
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable() {
            return VectorDrawableCompat(this)
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable(Resources resources) {
            return VectorDrawableCompat(this)
        }

        fun onStateChanged(Array<Int> iArr) {
            Boolean zOnStateChanged = this.mVPathRenderer.onStateChanged(iArr)
            this.mCacheDirty |= zOnStateChanged
            return zOnStateChanged
        }

        fun updateCacheStates() {
            this.mCachedTint = this.mTint
            this.mCachedTintMode = this.mTintMode
            this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha()
            this.mCachedAutoMirrored = this.mAutoMirrored
            this.mCacheDirty = false
        }

        fun updateCachedBitmap(Int i, Int i2) {
            this.mCachedBitmap.eraseColor(0)
            this.mVPathRenderer.draw(Canvas(this.mCachedBitmap), i, i2, null)
        }
    }

    @RequiresApi(24)
    class VectorDrawableDelegateState extends Drawable.ConstantState {
        private final Drawable.ConstantState mDelegateState

        constructor(Drawable.ConstantState constantState) {
            this.mDelegateState = constantState
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun canApplyTheme() {
            return this.mDelegateState.canApplyTheme()
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations()
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat()
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable()
            return vectorDrawableCompat
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat()
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(resources)
            return vectorDrawableCompat
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat()
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(resources, theme)
            return vectorDrawableCompat
        }
    }

    VectorDrawableCompat() {
        this.mAllowCaching = true
        this.mTmpFloats = new Float[9]
        this.mTmpMatrix = Matrix()
        this.mTmpBounds = Rect()
        this.mVectorState = VectorDrawableCompatState()
    }

    VectorDrawableCompat(@NonNull VectorDrawableCompatState vectorDrawableCompatState) {
        this.mAllowCaching = true
        this.mTmpFloats = new Float[9]
        this.mTmpMatrix = Matrix()
        this.mTmpBounds = Rect()
        this.mVectorState = vectorDrawableCompatState
        this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode)
    }

    static Int applyAlpha(Int i, Float f) {
        return (((Int) (Color.alpha(i) * f)) << 24) | (16777215 & i)
    }

    @Nullable
    fun create(@NonNull Resources resources, @DrawableRes Int i, @Nullable Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Int next
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat()
            vectorDrawableCompat.mDelegateDrawable = ResourcesCompat.getDrawable(resources, i, theme)
            vectorDrawableCompat.mCachedConstantStateDelegate = VectorDrawableDelegateState(vectorDrawableCompat.mDelegateDrawable.getConstantState())
            return vectorDrawableCompat
        }
        try {
            XmlResourceParser xml = resources.getXml(i)
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml)
            do {
                next = xml.next()
                if (next == 2) {
                    break
                }
            } while (next != 1);
            if (next != 2) {
                throw XmlPullParserException("No start tag found")
            }
            return createFromXmlInner(resources, (XmlPullParser) xml, attributeSetAsAttributeSet, theme)
        } catch (IOException e) {
            Log.e(LOGTAG, "parser error", e)
            return null
        } catch (XmlPullParserException e2) {
            Log.e(LOGTAG, "parser error", e2)
            return null
        }
    }

    fun createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat()
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme)
        return vectorDrawableCompat
    }

    private fun inflateInternal(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Boolean z
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer
        ArrayDeque arrayDeque = ArrayDeque()
        arrayDeque.push(vPathRenderer.mRootGroup)
        Int eventType = xmlPullParser.getEventType()
        Int depth = xmlPullParser.getDepth() + 1
        Boolean z2 = true
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName()
                VGroup vGroup = (VGroup) arrayDeque.peek()
                if (SHAPE_PATH.equals(name)) {
                    VFullPath vFullPath = VFullPath()
                    vFullPath.inflate(resources, attributeSet, theme, xmlPullParser)
                    vGroup.mChildren.add(vFullPath)
                    if (vFullPath.getPathName() != null) {
                        vPathRenderer.mVGTargetsMap.put(vFullPath.getPathName(), vFullPath)
                    }
                    z = false
                    vectorDrawableCompatState.mChangingConfigurations = vFullPath.mChangingConfigurations | vectorDrawableCompatState.mChangingConfigurations
                } else if (SHAPE_CLIP_PATH.equals(name)) {
                    VClipPath vClipPath = VClipPath()
                    vClipPath.inflate(resources, attributeSet, theme, xmlPullParser)
                    vGroup.mChildren.add(vClipPath)
                    if (vClipPath.getPathName() != null) {
                        vPathRenderer.mVGTargetsMap.put(vClipPath.getPathName(), vClipPath)
                    }
                    vectorDrawableCompatState.mChangingConfigurations |= vClipPath.mChangingConfigurations
                    z = z2
                } else {
                    if (SHAPE_GROUP.equals(name)) {
                        VGroup vGroup2 = VGroup()
                        vGroup2.inflate(resources, attributeSet, theme, xmlPullParser)
                        vGroup.mChildren.add(vGroup2)
                        arrayDeque.push(vGroup2)
                        if (vGroup2.getGroupName() != null) {
                            vPathRenderer.mVGTargetsMap.put(vGroup2.getGroupName(), vGroup2)
                        }
                        vectorDrawableCompatState.mChangingConfigurations |= vGroup2.mChangingConfigurations
                    }
                    z = z2
                }
            } else {
                if (eventType == 3 && SHAPE_GROUP.equals(xmlPullParser.getName())) {
                    arrayDeque.pop()
                }
                z = z2
            }
            z2 = z
            eventType = xmlPullParser.next()
        }
        if (z2) {
            throw XmlPullParserException("no path defined")
        }
    }

    private fun needMirroring() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1
        }
        return false
    }

    private static PorterDuff.Mode parseTintModeCompat(Int i, PorterDuff.Mode mode) {
        switch (i) {
            case 3:
                return PorterDuff.Mode.SRC_OVER
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
                return mode
            case 5:
                return PorterDuff.Mode.SRC_IN
            case 9:
                return PorterDuff.Mode.SRC_ATOP
            case 14:
                return PorterDuff.Mode.MULTIPLY
            case 15:
                return PorterDuff.Mode.SCREEN
            case 16:
                return PorterDuff.Mode.ADD
        }
    }

    private fun printGroupTree(VGroup vGroup, Int i) {
        Int i2 = 0
        String str = ""
        for (Int i3 = 0; i3 < i; i3++) {
            str = str + CodeWriter.INDENT
        }
        Log.v(LOGTAG, str + "current group is :" + vGroup.getGroupName() + " rotation is " + vGroup.mRotate)
        Log.v(LOGTAG, str + "matrix is :" + vGroup.getLocalMatrix().toString())
        while (true) {
            Int i4 = i2
            if (i4 >= vGroup.mChildren.size()) {
                return
            }
            VObject vObject = (VObject) vGroup.mChildren.get(i4)
            if (vObject is VGroup) {
                printGroupTree((VGroup) vObject, i + 1)
            } else {
                ((VPath) vObject).printVPath(i + 1)
            }
            i2 = i4 + 1
        }
    }

    private fun updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) throws XmlPullParserException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer
        vectorDrawableCompatState.mTintMode = parseTintModeCompat(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN)
        ColorStateList colorStateList = typedArray.getColorStateList(1)
        if (colorStateList != null) {
            vectorDrawableCompatState.mTint = colorStateList
        }
        vectorDrawableCompatState.mAutoMirrored = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "autoMirrored", 5, vectorDrawableCompatState.mAutoMirrored)
        vPathRenderer.mViewportWidth = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportWidth", 7, vPathRenderer.mViewportWidth)
        vPathRenderer.mViewportHeight = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportHeight", 8, vPathRenderer.mViewportHeight)
        if (vPathRenderer.mViewportWidth <= 0.0f) {
            throw XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0")
        }
        if (vPathRenderer.mViewportHeight <= 0.0f) {
            throw XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0")
        }
        vPathRenderer.mBaseWidth = typedArray.getDimension(3, vPathRenderer.mBaseWidth)
        vPathRenderer.mBaseHeight = typedArray.getDimension(2, vPathRenderer.mBaseHeight)
        if (vPathRenderer.mBaseWidth <= 0.0f) {
            throw XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0")
        }
        if (vPathRenderer.mBaseHeight <= 0.0f) {
            throw XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0")
        }
        vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "alpha", 4, vPathRenderer.getAlpha()))
        String string = typedArray.getString(0)
        if (string != null) {
            vPathRenderer.mRootName = string
            vPathRenderer.mVGTargetsMap.put(string, vPathRenderer)
        }
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit applyTheme(Resources.Theme theme) {
        super.applyTheme(theme)
    }

    @Override // android.graphics.drawable.Drawable
    fun canApplyTheme() {
        if (this.mDelegateDrawable == null) {
            return false
        }
        DrawableCompat.canApplyTheme(this.mDelegateDrawable)
        return false
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit clearColorFilter() {
        super.clearColorFilter()
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.draw(canvas)
            return
        }
        copyBounds(this.mTmpBounds)
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) {
            return
        }
        ColorFilter colorFilter = this.mColorFilter == null ? this.mTintFilter : this.mColorFilter
        canvas.getMatrix(this.mTmpMatrix)
        this.mTmpMatrix.getValues(this.mTmpFloats)
        Float fAbs = Math.abs(this.mTmpFloats[0])
        Float fAbs2 = Math.abs(this.mTmpFloats[4])
        Float fAbs3 = Math.abs(this.mTmpFloats[1])
        Float fAbs4 = Math.abs(this.mTmpFloats[3])
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs2 = 1.0f
            fAbs = 1.0f
        }
        Int iMin = Math.min(2048, (Int) (fAbs * this.mTmpBounds.width()))
        Int iMin2 = Math.min(2048, (Int) (fAbs2 * this.mTmpBounds.height()))
        if (iMin <= 0 || iMin2 <= 0) {
            return
        }
        Int iSave = canvas.save()
        canvas.translate(this.mTmpBounds.left, this.mTmpBounds.top)
        if (needMirroring()) {
            canvas.translate(this.mTmpBounds.width(), 0.0f)
            canvas.scale(-1.0f, 1.0f)
        }
        this.mTmpBounds.offsetTo(0, 0)
        this.mVectorState.createCachedBitmapIfNeeded(iMin, iMin2)
        if (!this.mAllowCaching) {
            this.mVectorState.updateCachedBitmap(iMin, iMin2)
        } else if (!this.mVectorState.canReuseCache()) {
            this.mVectorState.updateCachedBitmap(iMin, iMin2)
            this.mVectorState.updateCacheStates()
        }
        this.mVectorState.drawCachedBitmapWithRootAlpha(canvas, colorFilter, this.mTmpBounds)
        canvas.restoreToCount(iSave)
    }

    @Override // android.graphics.drawable.Drawable
    fun getAlpha() {
        return this.mDelegateDrawable != null ? DrawableCompat.getAlpha(this.mDelegateDrawable) : this.mVectorState.mVPathRenderer.getRootAlpha()
    }

    @Override // android.graphics.drawable.Drawable
    fun getChangingConfigurations() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getChangingConfigurations() : super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations()
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter()
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.mDelegateDrawable != null && Build.VERSION.SDK_INT >= 24) {
            return VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState())
        }
        this.mVectorState.mChangingConfigurations = getChangingConfigurations()
        return this.mVectorState
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent()
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicHeight() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getIntrinsicHeight() : (Int) this.mVectorState.mVPathRenderer.mBaseHeight
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicWidth() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getIntrinsicWidth() : (Int) this.mVectorState.mVPathRenderer.mBaseWidth
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getMinimumHeight() {
        return super.getMinimumHeight()
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getMinimumWidth() {
        return super.getMinimumWidth()
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getOpacity()
        }
        return -3
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Boolean getPadding(Rect rect) {
        return super.getPadding(rect)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getPixelSize() {
        if (this.mVectorState == null || this.mVectorState.mVPathRenderer == null || this.mVectorState.mVPathRenderer.mBaseWidth == 0.0f || this.mVectorState.mVPathRenderer.mBaseHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportWidth == 0.0f) {
            return 1.0f
        }
        Float f = this.mVectorState.mVPathRenderer.mBaseWidth
        Float f2 = this.mVectorState.mVPathRenderer.mBaseHeight
        return Math.min(this.mVectorState.mVPathRenderer.mViewportWidth / f, this.mVectorState.mVPathRenderer.mViewportHeight / f2)
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Array<Int> getState() {
        return super.getState()
    }

    Object getTargetByName(String str) {
        return this.mVectorState.mVPathRenderer.mVGTargetsMap.get(str)
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion()
    }

    @Override // android.graphics.drawable.Drawable
    fun inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.inflate(resources, xmlPullParser, attributeSet)
        } else {
            inflate(resources, xmlPullParser, attributeSet, null)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.inflate(this.mDelegateDrawable, resources, xmlPullParser, attributeSet, theme)
            return
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState
        vectorDrawableCompatState.mVPathRenderer = VPathRenderer()
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY)
        updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser)
        typedArrayObtainAttributes.recycle()
        vectorDrawableCompatState.mChangingConfigurations = getChangingConfigurations()
        vectorDrawableCompatState.mCacheDirty = true
        inflateInternal(resources, xmlPullParser, attributeSet, theme)
        this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode)
    }

    @Override // android.graphics.drawable.Drawable
    fun invalidateSelf() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.invalidateSelf()
        } else {
            super.invalidateSelf()
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun isAutoMirrored() {
        return this.mDelegateDrawable != null ? DrawableCompat.isAutoMirrored(this.mDelegateDrawable) : this.mVectorState.mAutoMirrored
    }

    @Override // android.graphics.drawable.Drawable
    fun isStateful() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.isStateful() : super.isStateful() || (this.mVectorState != null && (this.mVectorState.isStateful() || (this.mVectorState.mTint != null && this.mVectorState.mTint.isStateful())))
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit jumpToCurrentState() {
        super.jumpToCurrentState()
    }

    @Override // android.graphics.drawable.Drawable
    fun mutate() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.mutate()
        } else if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = VectorDrawableCompatState(this.mVectorState)
            this.mMutated = true
        }
        return this
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds(rect)
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onStateChange(Array<Int> iArr) {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState(iArr)
        }
        Boolean z = false
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState
        if (vectorDrawableCompatState.mTint != null && vectorDrawableCompatState.mTintMode != null) {
            this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode)
            invalidateSelf()
            z = true
        }
        if (!vectorDrawableCompatState.isStateful() || !vectorDrawableCompatState.onStateChanged(iArr)) {
            return z
        }
        invalidateSelf()
        return true
    }

    @Override // android.graphics.drawable.Drawable
    fun scheduleSelf(Runnable runnable, Long j) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.scheduleSelf(runnable, j)
        } else {
            super.scheduleSelf(runnable, j)
        }
    }

    Unit setAllowCaching(Boolean z) {
        this.mAllowCaching = z
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setAlpha(i)
        } else if (this.mVectorState.mVPathRenderer.getRootAlpha() != i) {
            this.mVectorState.mVPathRenderer.setRootAlpha(i)
            invalidateSelf()
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setAutoMirrored(Boolean z) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setAutoMirrored(this.mDelegateDrawable, z)
        } else {
            this.mVectorState.mAutoMirrored = z
        }
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setChangingConfigurations(Int i) {
        super.setChangingConfigurations(i)
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setColorFilter(Int i, PorterDuff.Mode mode) {
        super.setColorFilter(i, mode)
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter(colorFilter)
        } else {
            this.mColorFilter = colorFilter
            invalidateSelf()
        }
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setFilterBitmap(Boolean z) {
        super.setFilterBitmap(z)
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setHotspot(Float f, Float f2) {
        super.setHotspot(f, f2)
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
        super.setHotspotBounds(i, i2, i3, i4)
    }

    @Override // android.support.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Boolean setState(Array<Int> iArr) {
        return super.setState(iArr)
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTint(Int i) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTint(this.mDelegateDrawable, i)
        } else {
            setTintList(ColorStateList.valueOf(i))
        }
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTintList(ColorStateList colorStateList) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintList(this.mDelegateDrawable, colorStateList)
            return
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState
        if (vectorDrawableCompatState.mTint != colorStateList) {
            vectorDrawableCompatState.mTint = colorStateList
            this.mTintFilter = updateTintFilter(this.mTintFilter, colorStateList, vectorDrawableCompatState.mTintMode)
            invalidateSelf()
        }
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    fun setTintMode(PorterDuff.Mode mode) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintMode(this.mDelegateDrawable, mode)
            return
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState
        if (vectorDrawableCompatState.mTintMode != mode) {
            vectorDrawableCompatState.mTintMode = mode
            this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, mode)
            invalidateSelf()
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setVisible(Boolean z, Boolean z2) {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.setVisible(z, z2) : super.setVisible(z, z2)
    }

    @Override // android.graphics.drawable.Drawable
    fun unscheduleSelf(Runnable runnable) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.unscheduleSelf(runnable)
        } else {
            super.unscheduleSelf(runnable)
        }
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null
        }
        return PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode)
    }
}
