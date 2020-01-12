package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

data class Question(
  @StringRes val textResId: Int,
  val answer: Boolean
) {

  var isEnabled: Boolean = true
  private var providedAnswer: Boolean? = null

  fun submitAnswer(providedAnswer: Boolean) {
    this.providedAnswer = providedAnswer
    this.isEnabled = false;
  }

  fun isAnswered(): Boolean {
    return providedAnswer != null;
  }

  fun isCorrect(): Boolean {
    return if (providedAnswer == null) {
      false
    } else {
      providedAnswer == answer
    }
  }


}
