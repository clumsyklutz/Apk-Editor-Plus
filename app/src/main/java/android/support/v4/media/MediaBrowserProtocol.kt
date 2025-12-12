package android.support.v4.media

class MediaBrowserProtocol {
    public static val CLIENT_MSG_ADD_SUBSCRIPTION = 3
    public static val CLIENT_MSG_CONNECT = 1
    public static val CLIENT_MSG_DISCONNECT = 2
    public static val CLIENT_MSG_GET_MEDIA_ITEM = 5
    public static val CLIENT_MSG_REGISTER_CALLBACK_MESSENGER = 6
    public static val CLIENT_MSG_REMOVE_SUBSCRIPTION = 4
    public static val CLIENT_MSG_SEARCH = 8
    public static val CLIENT_MSG_SEND_CUSTOM_ACTION = 9
    public static val CLIENT_MSG_UNREGISTER_CALLBACK_MESSENGER = 7
    public static val CLIENT_VERSION_1 = 1
    public static val CLIENT_VERSION_CURRENT = 1
    public static val DATA_CALLBACK_TOKEN = "data_callback_token"
    public static val DATA_CALLING_UID = "data_calling_uid"
    public static val DATA_CUSTOM_ACTION = "data_custom_action"
    public static val DATA_CUSTOM_ACTION_EXTRAS = "data_custom_action_extras"
    public static val DATA_MEDIA_ITEM_ID = "data_media_item_id"
    public static val DATA_MEDIA_ITEM_LIST = "data_media_item_list"
    public static val DATA_MEDIA_SESSION_TOKEN = "data_media_session_token"
    public static val DATA_OPTIONS = "data_options"
    public static val DATA_PACKAGE_NAME = "data_package_name"
    public static val DATA_RESULT_RECEIVER = "data_result_receiver"
    public static val DATA_ROOT_HINTS = "data_root_hints"
    public static val DATA_SEARCH_EXTRAS = "data_search_extras"
    public static val DATA_SEARCH_QUERY = "data_search_query"
    public static val EXTRA_CLIENT_VERSION = "extra_client_version"
    public static val EXTRA_MESSENGER_BINDER = "extra_messenger"
    public static val EXTRA_SERVICE_VERSION = "extra_service_version"
    public static val EXTRA_SESSION_BINDER = "extra_session_binder"
    public static val SERVICE_MSG_ON_CONNECT = 1
    public static val SERVICE_MSG_ON_CONNECT_FAILED = 2
    public static val SERVICE_MSG_ON_LOAD_CHILDREN = 3
    public static val SERVICE_VERSION_1 = 1
    public static val SERVICE_VERSION_2 = 2
    public static val SERVICE_VERSION_CURRENT = 2

    MediaBrowserProtocol() {
    }
}
