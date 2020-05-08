package br.com.tecdev.covid19.util.location

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

fun drawableToUri(context: Context, drawableId: Int): Uri {
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.resources.getResourcePackageName(drawableId)
                + '/' + context.resources.getResourceTypeName(drawableId)
                + '/' + context.resources.getResourceEntryName(drawableId)
    )
}