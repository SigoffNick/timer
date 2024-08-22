import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timer.pages.home_page.widgets.ResetRoundButton

@Composable
fun PlayButton(
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onSwitch: () -> Unit,
) {

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = !isActive,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Button(
                onClick = onSwitch,
                modifier = Modifier.size(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(76.dp),
                    tint = Color.Black
                )
            }
        }

        AnimatedVisibility(
            visible = isActive,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Button(
                onClick = onSwitch,
                modifier = Modifier.size(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Pause Icon",
                    modifier = Modifier.size(76.dp),
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview()
@Composable()
fun PlayButtonPreview(){
    PlayButton (true, Modifier) {}
}
