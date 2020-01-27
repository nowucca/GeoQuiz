package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true)
  )

  var currentIndex = 0

  val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

  val currentQuestionText: Int
    get() = questionBank[currentIndex].textResId

  val currentQuestionEnabled: Boolean
    get() = questionBank[currentIndex].isEnabled

  val currentQuestion: Question
    get() = questionBank[currentIndex]

  val allQuestionsAnswered: Boolean
    get() = questionBank.all  { q -> q.isAnswered() }

  fun correctPercentage(): Float {
    return (questionBank.filter { it.isCorrect() }.size /
      questionBank.size.toFloat()) * 100
  }

  fun moveToNext() {
    currentIndex = (currentIndex + 1) % questionBank.size
  }

  fun moveToPrevious() {
    currentIndex = (currentIndex - 1)
    if (currentIndex < 0) currentIndex += questionBank.size
  }

}

