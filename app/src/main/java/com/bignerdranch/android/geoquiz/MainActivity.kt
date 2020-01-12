package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

  private lateinit var trueButton: Button
  private lateinit var falseButton: Button
  private lateinit var nextButton: ImageButton
  private lateinit var previousButton: ImageButton
  private lateinit var questionTextView: TextView

  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true)
  )

  private var currentIndex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate(Bundle?) called")
    setContentView(R.layout.activity_main)

    trueButton = findViewById(R.id.true_button)
    falseButton = findViewById(R.id.false_button)
    nextButton = findViewById(R.id.next_button)
    previousButton = findViewById(R.id.previous_button)
    questionTextView = findViewById(R.id.question_text_view)

    trueButton.setOnClickListener {
      checkAnswer(true)
    }

    falseButton.setOnClickListener {
      checkAnswer(false)
    }

    previousButton.setOnClickListener {
      previousQuestion()
    }

    nextButton.setOnClickListener {
      nextQuestion()
    }

    questionTextView.setOnClickListener {
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

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop() called")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy() called")
  }

  private fun previousQuestion() {
    currentIndex = (currentIndex - 1)
    if (currentIndex < 0) currentIndex += questionBank.size
    updateQuestion()
  }

  private fun nextQuestion() {
    currentIndex = (currentIndex + 1) % questionBank.size
    updateQuestion()
  }

  private fun updateQuestion() {
    val questionTextResId = questionBank[currentIndex].textResId
    questionTextView.setText(questionTextResId)
    trueButton.isEnabled = questionBank[currentIndex].isEnabled;
    falseButton.isEnabled = questionBank[currentIndex].isEnabled;
  }

  private fun checkAnswer(userAnswer: Boolean) {
    val question = questionBank[currentIndex];

    question.submitAnswer(userAnswer);


    val messageResId = if (question.isCorrect()) {
      R.string.correct_toast
    } else {
      R.string.incorrect_toast
    }

    val allQuestionsAnswered: Boolean = questionBank.all { q -> q.isAnswered() }
    if (allQuestionsAnswered) {
      val percentage: Float =
        (questionBank.filter { it.isCorrect() }.size / questionBank.size.toFloat()) * 100

      Toast.makeText(this, getString(R.string.percentage_text, percentage), Toast.LENGTH_SHORT)
        .show()
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
      .show()

    updateQuestion()
  }
}
