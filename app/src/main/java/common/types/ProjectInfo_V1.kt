package common.types

import java.io.Serializable

class ProjectInfo_V1 implements Serializable {
    public String apkPath
    public String decodeRootPath
    public ActivityState_V1 state
}
