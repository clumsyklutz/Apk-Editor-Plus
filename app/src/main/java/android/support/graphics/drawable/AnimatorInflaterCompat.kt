package android.support.graphics.drawable

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Build
import android.support.annotation.AnimatorRes
import android.support.annotation.RestrictTo
import android.support.v4.content.res.TypedArrayUtils
import android.support.v4.graphics.PathParser
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.util.Xml
import android.view.InflateException
import java.io.IOException
import java.util.ArrayList
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class AnimatorInflaterCompat {
    private static val DBG_ANIMATOR_INFLATER = false
    private static val MAX_NUM_POINTS = 100
    private static val TAG = "AnimatorInflater"
    private static val TOGETHER = 0
    private static val VALUE_TYPE_COLOR = 3
    private static val VALUE_TYPE_FLOAT = 0
    private static val VALUE_TYPE_INT = 1
    private static val VALUE_TYPE_PATH = 2
    private static val VALUE_TYPE_UNDEFINED = 4

    class PathDataEvaluator implements TypeEvaluator {
        private PathParser.Array<PathDataNode> mNodeArray

        PathDataEvaluator() {
        }

        PathDataEvaluator(PathParser.Array<PathDataNode> pathDataNodeArr) {
            this.mNodeArray = pathDataNodeArr
        }

        @Override // android.animation.TypeEvaluator
        public PathParser.Array<PathDataNode> evaluate(Float f, PathParser.Array<PathDataNode> pathDataNodeArr, PathParser.Array<PathDataNode> pathDataNodeArr2) {
            if (!PathParser.canMorph(pathDataNodeArr, pathDataNodeArr2)) {
                throw IllegalArgumentException("Can't interpolate between two incompatible pathData")
            }
            if (this.mNodeArray == null || !PathParser.canMorph(this.mNodeArray, pathDataNodeArr)) {
                this.mNodeArray = PathParser.deepCopyNodes(pathDataNodeArr)
            }
            for (Int i = 0; i < pathDataNodeArr.length; i++) {
                this.mNodeArray[i].interpolatePathDataNode(pathDataNodeArr[i], pathDataNodeArr2[i], f)
            }
            return this.mNodeArray
        }
    }

    private constructor() {
    }

    private fun createAnimatorFromXml(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, Float f) {
        return createAnimatorFromXml(context, resources, theme, xmlPullParser, Xml.asAttributeSet(xmlPullParser), null, 0, f)
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e1, code lost:
    
        if (r22 == null) goto L44
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e3, code lost:
    
        if (r13 == null) goto L44
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e5, code lost:
    
        r8 = new android.animation.Animator[r13.size()]
        r9 = r13.iterator()
        r6 = 0
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f5, code lost:
    
        if (r9.hasNext() == false) goto L58
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00f7, code lost:
    
        r8[r6] = (android.animation.Animator) r9.next()
        r6 = r6 + 1
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0103, code lost:
    
        if (r23 != 0) goto L45
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0105, code lost:
    
        r22.playTogether(r8)
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x010a, code lost:
    
        return r5
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x010b, code lost:
    
        r22.playSequentially(r8)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.animation.Animator createAnimatorFromXml(android.content.Context r17, android.content.res.Resources r18, android.content.res.Resources.Theme r19, org.xmlpull.v1.XmlPullParser r20, android.util.AttributeSet r21, android.animation.AnimatorSet r22, Int r23, Float r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.AnimatorInflaterCompat.createAnimatorFromXml(android.content.Context, android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.animation.AnimatorSet, Int, Float):android.animation.Animator")
    }

    private fun createNewKeyframe(Keyframe keyframe, Float f) {
        return keyframe.getType() == Float.TYPE ? Keyframe.ofFloat(f) : keyframe.getType() == Integer.TYPE ? Keyframe.ofInt(f) : Keyframe.ofObject(f)
    }

    private fun distributeKeyframes(Array<Keyframe> keyframeArr, Float f, Int i, Int i2) {
        Float f2 = f / ((i2 - i) + 2)
        while (i <= i2) {
            keyframeArr[i].setFraction(keyframeArr[i - 1].getFraction() + f2)
            i++
        }
    }

    private fun dumpKeyframes(Array<Object> objArr, String str) {
        if (objArr == null || objArr.length == 0) {
            return
        }
        Log.d(TAG, str)
        Int length = objArr.length
        for (Int i = 0; i < length; i++) {
            Keyframe keyframe = (Keyframe) objArr[i]
            Log.d(TAG, "Keyframe " + i + ": fraction " + (keyframe.getFraction() < 0.0f ? "null" : Float.valueOf(keyframe.getFraction())) + ", , value : " + (keyframe.hasValue() ? keyframe.getValue() : "null"))
        }
    }

    private fun getPVH(TypedArray typedArray, Int i, Int i2, Int i3, String str) {
        PropertyValuesHolder propertyValuesHolderOfInt
        TypedValue typedValuePeekValue = typedArray.peekValue(i2)
        Boolean z = typedValuePeekValue != null
        Int i4 = z ? typedValuePeekValue.type : 0
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i3)
        Boolean z2 = typedValuePeekValue2 != null
        Int i5 = z2 ? typedValuePeekValue2.type : 0
        if (i == 4) {
            i = ((z && isColorType(i4)) || (z2 && isColorType(i5))) ? 3 : 0
        }
        Boolean z3 = i == 0
        if (i == 2) {
            String string = typedArray.getString(i2)
            String string2 = typedArray.getString(i3)
            PathParser.Array<PathDataNode> pathDataNodeArrCreateNodesFromPathData = PathParser.createNodesFromPathData(string)
            PathParser.Array<PathDataNode> pathDataNodeArrCreateNodesFromPathData2 = PathParser.createNodesFromPathData(string2)
            if (pathDataNodeArrCreateNodesFromPathData != null || pathDataNodeArrCreateNodesFromPathData2 != null) {
                if (pathDataNodeArrCreateNodesFromPathData != null) {
                    PathDataEvaluator pathDataEvaluator = PathDataEvaluator()
                    if (pathDataNodeArrCreateNodesFromPathData2 == null) {
                        return PropertyValuesHolder.ofObject(str, pathDataEvaluator, pathDataNodeArrCreateNodesFromPathData)
                    }
                    if (PathParser.canMorph(pathDataNodeArrCreateNodesFromPathData, pathDataNodeArrCreateNodesFromPathData2)) {
                        return PropertyValuesHolder.ofObject(str, pathDataEvaluator, pathDataNodeArrCreateNodesFromPathData, pathDataNodeArrCreateNodesFromPathData2)
                    }
                    throw InflateException(" Can't morph from " + string + " to " + string2)
                }
                if (pathDataNodeArrCreateNodesFromPathData2 != null) {
                    return PropertyValuesHolder.ofObject(str, PathDataEvaluator(), pathDataNodeArrCreateNodesFromPathData2)
                }
            }
            return null
        }
        ArgbEvaluator argbEvaluator = i == 3 ? ArgbEvaluator.getInstance() : null
        if (z3) {
            if (z) {
                Float dimension = i4 == 5 ? typedArray.getDimension(i2, 0.0f) : typedArray.getFloat(i2, 0.0f)
                if (z2) {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, dimension, i5 == 5 ? typedArray.getDimension(i3, 0.0f) : typedArray.getFloat(i3, 0.0f))
                } else {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, dimension)
                }
            } else {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, i5 == 5 ? typedArray.getDimension(i3, 0.0f) : typedArray.getFloat(i3, 0.0f))
            }
        } else if (z) {
            Int dimension2 = i4 == 5 ? (Int) typedArray.getDimension(i2, 0.0f) : isColorType(i4) ? typedArray.getColor(i2, 0) : typedArray.getInt(i2, 0)
            if (z2) {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, dimension2, i5 == 5 ? (Int) typedArray.getDimension(i3, 0.0f) : isColorType(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0))
            } else {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, dimension2)
            }
        } else if (z2) {
            propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, i5 == 5 ? (Int) typedArray.getDimension(i3, 0.0f) : isColorType(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0))
        } else {
            propertyValuesHolderOfInt = null
        }
        if (propertyValuesHolderOfInt == null || argbEvaluator == null) {
            return propertyValuesHolderOfInt
        }
        propertyValuesHolderOfInt.setEvaluator(argbEvaluator)
        return propertyValuesHolderOfInt
    }

    private fun inferValueTypeFromValues(TypedArray typedArray, Int i, Int i2) {
        TypedValue typedValuePeekValue = typedArray.peekValue(i)
        Boolean z = typedValuePeekValue != null
        Int i3 = z ? typedValuePeekValue.type : 0
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i2)
        Boolean z2 = typedValuePeekValue2 != null
        return ((z && isColorType(i3)) || (z2 && isColorType(z2 ? typedValuePeekValue2.type : 0))) ? 3 : 0
    }

    private fun inferValueTypeOfKeyframe(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        Int i = 0
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_KEYFRAME)
        TypedValue typedValuePeekNamedValue = TypedArrayUtils.peekNamedValue(typedArrayObtainAttributes, xmlPullParser, "value", 0)
        if ((typedValuePeekNamedValue != null) && isColorType(typedValuePeekNamedValue.type)) {
            i = 3
        }
        typedArrayObtainAttributes.recycle()
        return i
    }

    private fun isColorType(Int i) {
        return i >= 28 && i <= 31
    }

    fun loadAnimator(Context context, @AnimatorRes Int i) {
        return Build.VERSION.SDK_INT >= 24 ? AnimatorInflater.loadAnimator(context, i) : loadAnimator(context, context.getResources(), context.getTheme(), i)
    }

    fun loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes Int i) {
        return loadAnimator(context, resources, theme, i, 1.0f)
    }

    fun loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes Int i, Float f) {
        XmlResourceParser animation = null
        try {
            try {
                animation = resources.getAnimation(i)
                return createAnimatorFromXml(context, resources, theme, animation, f)
            } catch (IOException e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i))
                notFoundException.initCause(e)
                throw notFoundException
            } catch (XmlPullParserException e2) {
                Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i))
                notFoundException2.initCause(e2)
                throw notFoundException2
            }
        } finally {
            if (animation != null) {
                animation.close()
            }
        }
    }

    private fun loadAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, Float f, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR)
        TypedArray typedArrayObtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR)
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator()
        }
        parseAnimatorFromTypeArray(valueAnimator, typedArrayObtainAttributes, typedArrayObtainAttributes2, f, xmlPullParser)
        Int namedResourceId = TypedArrayUtils.getNamedResourceId(typedArrayObtainAttributes, xmlPullParser, "interpolator", 0, 0)
        if (namedResourceId > 0) {
            valueAnimator.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, namedResourceId))
        }
        typedArrayObtainAttributes.recycle()
        if (typedArrayObtainAttributes2 != null) {
            typedArrayObtainAttributes2.recycle()
        }
        return valueAnimator
    }

    private fun loadKeyframe(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, Int i, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_KEYFRAME)
        Keyframe keyframeOfFloat = null
        Float namedFloat = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "fraction", 3, -1.0f)
        TypedValue typedValuePeekNamedValue = TypedArrayUtils.peekNamedValue(typedArrayObtainAttributes, xmlPullParser, "value", 0)
        Boolean z = typedValuePeekNamedValue != null
        if (i == 4) {
            i = (z && isColorType(typedValuePeekNamedValue.type)) ? 3 : 0
        }
        if (z) {
            switch (i) {
                case 0:
                    keyframeOfFloat = Keyframe.ofFloat(namedFloat, TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "value", 0, 0.0f))
                    break
                case 1:
                case 3:
                    keyframeOfFloat = Keyframe.ofInt(namedFloat, TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "value", 0, 0))
                    break
            }
        } else {
            keyframeOfFloat = i == 0 ? Keyframe.ofFloat(namedFloat) : Keyframe.ofInt(namedFloat)
        }
        Int namedResourceId = TypedArrayUtils.getNamedResourceId(typedArrayObtainAttributes, xmlPullParser, "interpolator", 1, 0)
        if (namedResourceId > 0) {
            keyframeOfFloat.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, namedResourceId))
        }
        typedArrayObtainAttributes.recycle()
        return keyframeOfFloat
    }

    private fun loadObjectAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, Float f, XmlPullParser xmlPullParser) {
        ObjectAnimator objectAnimator = ObjectAnimator()
        loadAnimator(context, resources, theme, attributeSet, objectAnimator, f, xmlPullParser)
        return objectAnimator
    }

    private fun loadPvh(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, String str, Int i) throws XmlPullParserException, IOException {
        Int size
        Int i2
        ArrayList arrayList
        ArrayList arrayList2 = null
        Int iInferValueTypeOfKeyframe = i
        while (true) {
            Int next = xmlPullParser.next()
            if (next == 3 || next == 1) {
                break
            }
            if (xmlPullParser.getName().equals("keyframe")) {
                if (iInferValueTypeOfKeyframe == 4) {
                    iInferValueTypeOfKeyframe = inferValueTypeOfKeyframe(resources, theme, Xml.asAttributeSet(xmlPullParser), xmlPullParser)
                }
                Keyframe keyframeLoadKeyframe = loadKeyframe(context, resources, theme, Xml.asAttributeSet(xmlPullParser), iInferValueTypeOfKeyframe, xmlPullParser)
                if (keyframeLoadKeyframe != null) {
                    arrayList = arrayList2 == null ? ArrayList() : arrayList2
                    arrayList.add(keyframeLoadKeyframe)
                } else {
                    arrayList = arrayList2
                }
                xmlPullParser.next()
            } else {
                arrayList = arrayList2
            }
            arrayList2 = arrayList
        }
        if (arrayList2 == null || (size = arrayList2.size()) <= 0) {
            return null
        }
        Keyframe keyframe = (Keyframe) arrayList2.get(0)
        Keyframe keyframe2 = (Keyframe) arrayList2.get(size - 1)
        Float fraction = keyframe2.getFraction()
        if (fraction >= 1.0f) {
            i2 = size
        } else if (fraction < 0.0f) {
            keyframe2.setFraction(1.0f)
            i2 = size
        } else {
            arrayList2.add(arrayList2.size(), createNewKeyframe(keyframe2, 1.0f))
            i2 = size + 1
        }
        Float fraction2 = keyframe.getFraction()
        if (fraction2 != 0.0f) {
            if (fraction2 < 0.0f) {
                keyframe.setFraction(0.0f)
            } else {
                arrayList2.add(0, createNewKeyframe(keyframe, 0.0f))
                i2++
            }
        }
        Array<Keyframe> keyframeArr = new Keyframe[i2]
        arrayList2.toArray(keyframeArr)
        for (Int i3 = 0; i3 < i2; i3++) {
            Keyframe keyframe3 = keyframeArr[i3]
            if (keyframe3.getFraction() < 0.0f) {
                if (i3 == 0) {
                    keyframe3.setFraction(0.0f)
                } else if (i3 == i2 - 1) {
                    keyframe3.setFraction(1.0f)
                } else {
                    Int i4 = i3
                    for (Int i5 = i3 + 1; i5 < i2 - 1 && keyframeArr[i5].getFraction() < 0.0f; i5++) {
                        i4 = i5
                    }
                    distributeKeyframes(keyframeArr, keyframeArr[i4 + 1].getFraction() - keyframeArr[i3 - 1].getFraction(), i3, i4)
                }
            }
        }
        PropertyValuesHolder propertyValuesHolderOfKeyframe = PropertyValuesHolder.ofKeyframe(str, keyframeArr)
        if (iInferValueTypeOfKeyframe != 3) {
            return propertyValuesHolderOfKeyframe
        }
        propertyValuesHolderOfKeyframe.setEvaluator(ArgbEvaluator.getInstance())
        return propertyValuesHolderOfKeyframe
    }

    private static Array<PropertyValuesHolder> loadValues(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        ArrayList arrayList
        ArrayList arrayList2 = null
        while (true) {
            Int eventType = xmlPullParser.getEventType()
            if (eventType == 3 || eventType == 1) {
                break
            }
            if (eventType != 2) {
                xmlPullParser.next()
            } else {
                if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                    TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER)
                    String namedString = TypedArrayUtils.getNamedString(typedArrayObtainAttributes, xmlPullParser, "propertyName", 3)
                    Int namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "valueType", 2, 4)
                    PropertyValuesHolder propertyValuesHolderLoadPvh = loadPvh(context, resources, theme, xmlPullParser, namedString, namedInt)
                    PropertyValuesHolder pvh = propertyValuesHolderLoadPvh == null ? getPVH(typedArrayObtainAttributes, namedInt, 0, 1, namedString) : propertyValuesHolderLoadPvh
                    if (pvh != null) {
                        arrayList = arrayList2 == null ? ArrayList() : arrayList2
                        arrayList.add(pvh)
                    } else {
                        arrayList = arrayList2
                    }
                    typedArrayObtainAttributes.recycle()
                } else {
                    arrayList = arrayList2
                }
                xmlPullParser.next()
                arrayList2 = arrayList
            }
        }
        if (arrayList2 == null) {
            return null
        }
        Int size = arrayList2.size()
        Array<PropertyValuesHolder> propertyValuesHolderArr = new PropertyValuesHolder[size]
        for (Int i = 0; i < size; i++) {
            propertyValuesHolderArr[i] = (PropertyValuesHolder) arrayList2.get(i)
        }
        return propertyValuesHolderArr
    }

    private fun parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, Float f, XmlPullParser xmlPullParser) {
        Long namedInt = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "duration", 1, 300)
        Long namedInt2 = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "startOffset", 2, 0)
        Int namedInt3 = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 7, 4)
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueFrom") && TypedArrayUtils.hasAttribute(xmlPullParser, "valueTo")) {
            if (namedInt3 == 4) {
                namedInt3 = inferValueTypeFromValues(typedArray, 5, 6)
            }
            PropertyValuesHolder pvh = getPVH(typedArray, namedInt3, 5, 6, "")
            if (pvh != null) {
                valueAnimator.setValues(pvh)
            }
        }
        valueAnimator.setDuration(namedInt)
        valueAnimator.setStartDelay(namedInt2)
        valueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatCount", 3, 0))
        valueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatMode", 4, 1))
        if (typedArray2 != null) {
            setupObjectAnimator(valueAnimator, typedArray2, namedInt3, f, xmlPullParser)
        }
    }

    private fun setupObjectAnimator(ValueAnimator valueAnimator, TypedArray typedArray, Int i, Float f, XmlPullParser xmlPullParser) {
        ObjectAnimator objectAnimator = (ObjectAnimator) valueAnimator
        String namedString = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "pathData", 1)
        if (namedString == null) {
            objectAnimator.setPropertyName(TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyName", 0))
            return
        }
        String namedString2 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyXName", 2)
        String namedString3 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyYName", 3)
        if (namedString2 == null && namedString3 == null) {
            throw InflateException(typedArray.getPositionDescription() + " propertyXName or propertyYName is needed for PathData")
        }
        setupPathMotion(PathParser.createPathFromPathData(namedString), objectAnimator, 0.5f * f, namedString2, namedString3)
    }

    private fun setupPathMotion(Path path, ObjectAnimator objectAnimator, Float f, String str, String str2) {
        Int i
        PathMeasure pathMeasure = PathMeasure(path, false)
        Float length = 0.0f
        ArrayList arrayList = ArrayList()
        arrayList.add(Float.valueOf(0.0f))
        do {
            length += pathMeasure.getLength()
            arrayList.add(Float.valueOf(length))
        } while (pathMeasure.nextContour());
        PathMeasure pathMeasure2 = PathMeasure(path, false)
        Int iMin = Math.min(100, ((Int) (length / f)) + 1)
        Array<Float> fArr = new Float[iMin]
        Array<Float> fArr2 = new Float[iMin]
        Array<Float> fArr3 = new Float[2]
        Float f2 = length / (iMin - 1)
        Float f3 = 0.0f
        Int i2 = 0
        Int i3 = 0
        while (i3 < iMin) {
            pathMeasure2.getPosTan(f3 - ((Float) arrayList.get(i2)).floatValue(), fArr3, null)
            fArr[i3] = fArr3[0]
            fArr2[i3] = fArr3[1]
            f3 += f2
            if (i2 + 1 >= arrayList.size() || f3 <= ((Float) arrayList.get(i2 + 1)).floatValue()) {
                i = i2
            } else {
                i = i2 + 1
                pathMeasure2.nextContour()
            }
            i3++
            i2 = i
        }
        PropertyValuesHolder propertyValuesHolderOfFloat = str != null ? PropertyValuesHolder.ofFloat(str, fArr) : null
        PropertyValuesHolder propertyValuesHolderOfFloat2 = str2 != null ? PropertyValuesHolder.ofFloat(str2, fArr2) : null
        if (propertyValuesHolderOfFloat == null) {
            objectAnimator.setValues(propertyValuesHolderOfFloat2)
        } else if (propertyValuesHolderOfFloat2 == null) {
            objectAnimator.setValues(propertyValuesHolderOfFloat)
        } else {
            objectAnimator.setValues(propertyValuesHolderOfFloat, propertyValuesHolderOfFloat2)
        }
    }
}
