/*
Copyright 2017 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.stepstone.apprating

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.ColorRes
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import com.stepstone.apprating.listener.OnRatingBarChangedListener
import com.stepstone.apprating.ratingbar.CustomRatingBar

/**
 * This class represents custom dialog view which contains
 * rating bar, edit box and labels.
 */
class AppRatingDialogView(context: Context) : LinearLayout(context), OnRatingBarChangedListener {

    private lateinit var ratingBar: CustomRatingBar

    private var editText: EditText? = null

    private var titleText: TextView? = null

    private var contentText: TextView? = null

    private var noteDescriptionText: TextView? = null

    private var noteDescriptions: List<String>? = null

    init {
        setup(context)
    }

    /**
     * This method returns current rating.

     * @return number of current selected stars
     */
    val rateNumber: Float
        get() = ratingBar.rating

    /**
     * This method returns rating comment.

     * @return comment text from edit box
     */
    val comment: String
        get() = editText!!.text.toString()

    /**
     * This method sets maximum numbers of start which are visible.

     * @param numberOfStars maximum number of stars
     */
    fun setNumberOfStars(numberOfStars: Int) {
        ratingBar.setNumStars(numberOfStars)
    }

    /**
     * This method sets note descriptions for each rating value.

     * @param noteDescriptions list of note descriptions
     */
    fun setNoteDescriptions(noteDescriptions: List<String>) {
        val numberOfStars = noteDescriptions.size
        setNumberOfStars(numberOfStars)
        this.noteDescriptions = noteDescriptions
    }

    /**
     * This method sets default number of stars.

     * @param defaultRating number of stars
     */
    fun setDefaultRating(defaultRating: Int) {
        ratingBar.setRating(defaultRating)
    }

    /**
     * This method sets dialog's title.

     * @param title dialog's title text
     */
    fun setTitleText(title: String) {
        titleText!!.text = title
        titleText!!.visibility = View.VISIBLE
    }

    /**
     * This method sets dialog's content text.

     * @param content dialog's content text
     */
    fun setContentText(content: String) {
        contentText!!.text = content
        contentText!!.visibility = View.VISIBLE
    }

    /**
     * This method sets color of dialog's title.

     * @param color resource id of title label color
     */
    fun setTitleColor(@ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleText!!.setTextColor(resources.getColor(color, theme))
        } else {
            titleText!!.setTextColor(resources.getColor(color))

        }
    }

    /**
     * This method sets color of dialog's content.

     * @param color resource id of content label color
     */
    fun setContentColor(@ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentText!!.setTextColor(resources.getColor(color, theme))
        } else {
            contentText!!.setTextColor(resources.getColor(color))

        }
    }

    override fun onRatingChanged(rating: Int) {
        updateNoteDescriptionText(rating - 1)
    }

    private fun updateNoteDescriptionText(rating: Int) {
        if (noteDescriptions == null || noteDescriptions!!.isEmpty()) {
            noteDescriptionText!!.visibility = View.GONE
            return
        }

        val text = noteDescriptions!![rating]
        noteDescriptionText!!.text = text
        noteDescriptionText!!.visibility = View.VISIBLE
    }

    private fun setup(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.component_app_rate_dialog, this, true)
        ratingBar = findViewById(R.id.app_rate_dialog_rating_bar) as CustomRatingBar
        editText = findViewById(R.id.app_rate_dialog_edit_text) as EditText
        titleText = findViewById(R.id.app_rate_dialog_title_text) as TextView
        contentText = findViewById(R.id.app_rate_dialog_content_text) as TextView
        noteDescriptionText = findViewById(R.id.app_rate_dialog_note_description) as TextView
        ratingBar.setIsIndicator(false)
        ratingBar.setOnRatingBarChangeListener(this)
    }

    private val theme: Resources.Theme
        get() = context.theme
}