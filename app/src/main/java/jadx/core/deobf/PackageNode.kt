package jadx.core.deobf

import java.util.ArrayList
import java.util.Collections
import java.util.List
import java.util.Stack

class PackageNode {
    private static val SEPARATOR_CHAR = '.'
    private String cachedPackageFullAlias
    private String cachedPackageFullName
    private String packageAlias
    private final String packageName
    private List innerPackages = Collections.emptyList()
    private PackageNode parentPackage = this

    constructor(String str) {
        this.packageName = str
    }

    private fun getParentPackages() {
        Stack stack = Stack()
        for (PackageNode parentPackage = getParentPackage(); this != parentPackage; parentPackage = parentPackage.getParentPackage()) {
            stack.push(this)
            this = parentPackage
        }
        return stack
    }

    fun addInnerPackage(PackageNode packageNode) {
        if (this.innerPackages.isEmpty()) {
            this.innerPackages = ArrayList()
        }
        this.innerPackages.add(packageNode)
        packageNode.parentPackage = this
    }

    fun getAlias() {
        return this.packageAlias != null ? this.packageAlias : this.packageName
    }

    fun getFullAlias() {
        if (this.cachedPackageFullAlias == null) {
            Stack parentPackages = getParentPackages()
            StringBuilder sb = StringBuilder()
            if (parentPackages.size() > 0) {
                sb.append(((PackageNode) parentPackages.pop()).getAlias())
                while (parentPackages.size() > 0) {
                    sb.append(SEPARATOR_CHAR)
                    sb.append(((PackageNode) parentPackages.pop()).getAlias())
                }
            } else {
                sb.append(getAlias())
            }
            this.cachedPackageFullAlias = sb.toString()
        }
        return this.cachedPackageFullAlias
    }

    fun getFullName() {
        if (this.cachedPackageFullName == null) {
            Stack parentPackages = getParentPackages()
            StringBuilder sb = StringBuilder()
            sb.append(((PackageNode) parentPackages.pop()).getName())
            while (parentPackages.size() > 0) {
                sb.append(SEPARATOR_CHAR)
                sb.append(((PackageNode) parentPackages.pop()).getName())
            }
            this.cachedPackageFullName = sb.toString()
        }
        return this.cachedPackageFullName
    }

    fun getInnerPackageByName(String str) {
        for (PackageNode packageNode : this.innerPackages) {
            if (packageNode.getName().equals(str)) {
                return packageNode
            }
        }
        return null
    }

    fun getInnerPackages() {
        return this.innerPackages
    }

    fun getName() {
        return this.packageName
    }

    fun getParentPackage() {
        return this.parentPackage
    }

    fun hasAlias() {
        return this.packageAlias != null
    }

    fun setAlias(String str) {
        this.packageAlias = str
    }
}
