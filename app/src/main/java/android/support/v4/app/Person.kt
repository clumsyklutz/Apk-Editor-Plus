package android.support.v4.app

import android.app.Person
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.graphics.drawable.IconCompat

class Person {
    private static val ICON_KEY = "icon"
    private static val IS_BOT_KEY = "isBot"
    private static val IS_IMPORTANT_KEY = "isImportant"
    private static val KEY_KEY = "key"
    private static val NAME_KEY = "name"
    private static val URI_KEY = "uri"

    @Nullable
    IconCompat mIcon
    Boolean mIsBot
    Boolean mIsImportant

    @Nullable
    String mKey

    @Nullable
    CharSequence mName

    @Nullable
    String mUri

    class Builder {

        @Nullable
        IconCompat mIcon
        Boolean mIsBot
        Boolean mIsImportant

        @Nullable
        String mKey

        @Nullable
        CharSequence mName

        @Nullable
        String mUri

        Builder(Person person) {
            this.mName = person.mName
            this.mIcon = person.mIcon
            this.mUri = person.mUri
            this.mKey = person.mKey
            this.mIsBot = person.mIsBot
            this.mIsImportant = person.mIsImportant
        }

        @NonNull
        fun build() {
            return Person(this)
        }

        @NonNull
        fun setBot(Boolean z) {
            this.mIsBot = z
            return this
        }

        @NonNull
        fun setIcon(@Nullable IconCompat iconCompat) {
            this.mIcon = iconCompat
            return this
        }

        @NonNull
        fun setImportant(Boolean z) {
            this.mIsImportant = z
            return this
        }

        @NonNull
        fun setKey(@Nullable String str) {
            this.mKey = str
            return this
        }

        @NonNull
        fun setName(@Nullable CharSequence charSequence) {
            this.mName = charSequence
            return this
        }

        @NonNull
        fun setUri(@Nullable String str) {
            this.mUri = str
            return this
        }
    }

    Person(Builder builder) {
        this.mName = builder.mName
        this.mIcon = builder.mIcon
        this.mUri = builder.mUri
        this.mKey = builder.mKey
        this.mIsBot = builder.mIsBot
        this.mIsImportant = builder.mIsImportant
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun fromAndroidPerson(@NonNull android.app.Person person) {
        return Builder().setName(person.getName()).setIcon(person.getIcon() != null ? IconCompat.createFromIcon(person.getIcon()) : null).setUri(person.getUri()).setKey(person.getKey()).setBot(person.isBot()).setImportant(person.isImportant()).build()
    }

    @NonNull
    fun fromBundle(@NonNull Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(ICON_KEY)
        return Builder().setName(bundle.getCharSequence(NAME_KEY)).setIcon(bundle2 != null ? IconCompat.createFromBundle(bundle2) : null).setUri(bundle.getString(URI_KEY)).setKey(bundle.getString(KEY_KEY)).setBot(bundle.getBoolean(IS_BOT_KEY)).setImportant(bundle.getBoolean(IS_IMPORTANT_KEY)).build()
    }

    @Nullable
    fun getIcon() {
        return this.mIcon
    }

    @Nullable
    fun getKey() {
        return this.mKey
    }

    @Nullable
    fun getName() {
        return this.mName
    }

    @Nullable
    fun getUri() {
        return this.mUri
    }

    fun isBot() {
        return this.mIsBot
    }

    fun isImportant() {
        return this.mIsImportant
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public android.app.Person toAndroidPerson() {
        return new Person.Builder().setName(getName()).setIcon(getIcon() != null ? getIcon().toIcon() : null).setUri(getUri()).setKey(getKey()).setBot(isBot()).setImportant(isImportant()).build()
    }

    @NonNull
    fun toBuilder() {
        return Builder(this)
    }

    @NonNull
    fun toBundle() {
        Bundle bundle = Bundle()
        bundle.putCharSequence(NAME_KEY, this.mName)
        bundle.putBundle(ICON_KEY, this.mIcon != null ? this.mIcon.toBundle() : null)
        bundle.putString(URI_KEY, this.mUri)
        bundle.putString(KEY_KEY, this.mKey)
        bundle.putBoolean(IS_BOT_KEY, this.mIsBot)
        bundle.putBoolean(IS_IMPORTANT_KEY, this.mIsImportant)
        return bundle
    }
}
