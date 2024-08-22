import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.pages.home_page.widgets.toMinutesSeconds
import kotlin.time.Duration

@Composable
fun MainTimer(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    strokeWidth: Float = 50f,
    time: Duration,
    currentRound: Int,
    totalRounds: Int,
) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "")

    Box(
        modifier = modifier,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = time.toMinutesSeconds(),
            color = Color.White,
            fontSize = 120.sp,
        )
        SegmentedCircle(
            totalRounds = totalRounds,
            strokeWidth = strokeWidth,
            modifier = modifier.size(400.dp),
            currentRound = currentRound,
            color = color
        )
        Canvas(modifier = modifier.size(350.dp)) {
            drawCircle(
                color = color.copy(alpha = 0.3f),
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * -animatedProgress,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

@Composable
fun SegmentedCircle(
    totalRounds: Int,
    currentRound: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    strokeWidth: Float = 30f
) {
    Canvas(modifier = modifier.size(350.dp)) {
        val segmentAngle = 360f / totalRounds
        for (i in 0 until totalRounds) {
            drawArc(
                color = when {
                    (i < currentRound - 1) -> color.copy(alpha = 0.3f)
                    i == currentRound - 1 -> color
                    else -> Color.White
                },
                startAngle = i * segmentAngle - 90f,
                sweepAngle = segmentAngle - 2,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
        }
    }
}