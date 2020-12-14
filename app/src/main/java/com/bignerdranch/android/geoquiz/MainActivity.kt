package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private val quizViewModel: QuizViewModel by lazy {
    ViewModelProvider(this).get(QuizViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate(Bundle?) called")
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val provider = ViewModelProvider(this)
    val quizViewModel = provider.get(QuizViewModel::class.java)
    Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

    quizViewModel.currentIndex =
      savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

    binding.trueButton.setOnClickListener {
      checkAnswer(true)
    }

    binding.falseButton.setOnClickListener {
      checkAnswer(false)
    }

    binding.previousButton.setOnClickListener {
      previousQuestion()
    }

    binding.nextButton.setOnClickListener {
      nextQuestion()
    }

    binding.questionTextView.setOnClickListener {
      nextQuestion()
    }

    updateQuestion()
  }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart() called")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume() called")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause() called")
  }

  override fun onSaveInstanceState(savedInstanceState: Bundle) {
    super.onSaveInstanceState(savedInstanceState)
    Log.i(TAG, "onSaveInstanceState")
    savedInstanceState.putInt(KEY_INDEX,  quizViewModel.currentIndex)
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop() called")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy() called")
  }

  private fun previousQuestion() {
    quizViewModel.moveToPrevious()
    updateQuestion()
  }

  private fun nextQuestion() {
    quizViewModel.moveToNext()
    updateQuestion()
  }

  private fun updateQuestion() {
    val questionTextResId = quizViewModel.currentQuestionText
    binding.questionTextView.setText(questionTextResId)
    binding.trueButton.isEnabled = quizViewModel.currentQuestionEnabled
    binding.falseButton.isEnabled = quizViewModel.currentQuestionEnabled
  }

  private fun checkAnswer(userAnswer: Boolean) {
    val question = quizViewModel.currentQuestion

    question.submitAnswer(userAnswer)


    val messageResId = if (question.isCorrect()) {
      R.string.correct_toast
    } else {
      R.string.incorrect_toast
    }

    if (quizViewModel.allQuestionsAnswered) {
      Toast.makeText(this, getString(R.string.percentage_text, quizViewModel.correctPercentage()), Toast.LENGTH_SHORT)
        .show()
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
      .show()

    updateQuestion()
  }
}
