package net.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import net.basicmodel.R
import java.util.*

class ResourceManager {
    val bg_part1 = "https:magichua.club/preview/img/bg_"
    val bg_part2 = ".jpg"

    companion object {
        private var instance: ResourceManager? = null
            get() {
                field?.let {

                } ?: run {
                    field = ResourceManager()
                }
                return field
            }

        @Synchronized
        fun get(): ResourceManager {
            return instance!!
        }
    }

    fun getBackgroundRes(): ArrayList<String> {
        val result = ArrayList<String>()
        for (i in 1..31) {
            val url = bg_part1 + i + bg_part2
            result.add(url)
        }
        return result
    }

    fun getAnimRes(context: Context): ArrayList<String> {
        val result = ArrayList<String>()
        result.add(getResId2Str(context, R.drawable.anim1))
        result.add(getResId2Str(context, R.drawable.anim3))
        result.add(getResId2Str(context, R.drawable.anim4))
        result.add(getResId2Str(context, R.drawable.anim7))
        result.add(getResId2Str(context, R.drawable.anim8))
        result.add(getResId2Str(context, R.drawable.anim10))
        result.add(getResId2Str(context, R.drawable.anim11))
        result.add(getResId2Str(context, R.drawable.anim13))
        result.add(getResId2Str(context, R.drawable.anim14))
        result.add(getResId2Str(context, R.drawable.anim18))
        result.add(getResId2Str(context, R.drawable.anim20))
        result.add(getResId2Str(context, R.drawable.anim22))
        result.add(getResId2Str(context, R.drawable.anim23))
        result.add(getResId2Str(context, R.drawable.anim24))
        result.add(getResId2Str(context, R.drawable.anim25))
        result.add(getResId2Str(context, R.drawable.anim26))
        result.add(getResId2Str(context, R.drawable.anim27))
        result.add(getResId2Str(context, R.drawable.anim28))
        result.add(getResId2Str(context, R.drawable.gif_1))
        result.add(getResId2Str(context, R.drawable.gif_2))
        result.add(getResId2Str(context, R.drawable.gif_3))
        result.add(getResId2Str(context, R.drawable.gif_5))
        result.add(getResId2Str(context, R.drawable.gif_6))
        result.add(getResId2Str(context, R.drawable.gif_7))
        result.add(getResId2Str(context, R.drawable.gif_17))
        result.add(getResId2Str(context, R.drawable.gif_18))
        result.add(getResId2Str(context, R.drawable.gif_19))
        result.add(getResId2Str(context, R.drawable.gif_21))
        result.add(getResId2Str(context, R.drawable.gif_23))
        result.add(getResId2Str(context, R.drawable.gif_24))
        result.add(getResId2Str(context, R.drawable.gif_26))
        result.add(getResId2Str(context, R.drawable.gif_31))
        result.add(getResId2Str(context, R.drawable.gif_32))
        result.add(getResId2Str(context, R.drawable.gif_33))
        result.add(getResId2Str(context, R.drawable.gif_34))
        return result
    }

    fun getResId2Str(context: Context, id: Int): String {
        val resources = context.resources
        val url = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(id) + "/"
                    + resources.getResourceTypeName(id) + "/"
                    + resources.getResourceEntryName(id)
        )
        return url.toString()
    }
}