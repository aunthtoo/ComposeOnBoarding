package com.aunkhtoo.composeonboarding.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aunkhtoo.composeonboarding.R
import com.aunkhtoo.composeonboarding.screen.SharedViewModel

/**
Created By Aunt Htoo Aung on 22/11/2023.
 **/

@Composable
fun WelcomeScreen(
  viewModel: SharedViewModel,
  onGetStartedClick: () -> Unit
) {

  Column(modifier = Modifier.padding(bottom = 25.dp, start = 16.dp, top = 16.dp, end = 16.dp)) {

    ConstraintLayout(
      modifier = Modifier
        .fillMaxSize()
        .weight(1f)
    ) {

      val (tvWelcome, tvHello, image, tvDesc, btnStart) = createRefs()

      Text(
        text = "Welcome to DailyVita",
        fontSize = 28.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.constrainAs(tvWelcome) {
          linkTo(start = parent.start, end = parent.end)
          bottom.linkTo(tvHello.top, margin = 20.dp)
          width = Dimension.fillToConstraints
        })

      Text(
        text = "Hello, we are here to make your life healthier and happier",
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.constrainAs(tvHello) {
          linkTo(start = parent.start, end = parent.end)
          bottom.linkTo(image.top, margin = 50.dp)
          width = Dimension.fillToConstraints
        })

      Image(
        painter = painterResource(id = R.drawable.conversation_image),
        contentDescription = null,
        modifier = Modifier.constrainAs(image) {
          linkTo(start = parent.start, end = parent.end, top = parent.top, bottom = parent.bottom)
          width = Dimension.fillToConstraints
          height = Dimension.wrapContent
        })

      Text(
        text = "We will ask couple of questions to better understand your vitamin need.",
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.constrainAs(tvDesc) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(image.bottom)
          width = Dimension.fillToConstraints
          height = Dimension.wrapContent
        }
      )
    }

    Button(
      onClick = { onGetStartedClick() },
      modifier = Modifier.fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
      shape = RoundedCornerShape(size = 10.dp),
      elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
      Text(text = "Get Started", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
    }
  }

}