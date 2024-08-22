import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.pages.home_page.widgets.SegmentedCircle
import kotlin.time.Duration
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun MainTimer(
    progress: Float,
    color: Color,
    strokeWidth: Float = 40f,
    time: Duration,
    currentRound: Int,
    totalRounds: Int,
) {

    val configuration = LocalConfiguration.current

    val orientation = configuration.orientation

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            val screenHeightDp = configuration.screenHeightDp.dp
            _MainTimer(
                progress = progress,
                color = color,
                strokeWidth = strokeWidth,
                time = time,
                currentRound = currentRound,
                totalRounds = totalRounds,
                modifier = Modifier.size(screenHeightDp.div(1.3f))
            )
        }

        Configuration.ORIENTATION_PORTRAIT -> {
            val screenWidthDp = configuration.screenWidthDp.dp
            _MainTimer(
                progress = progress,
                color = color,
                strokeWidth = strokeWidth,
                time = time,
                currentRound = currentRound,
                totalRounds = totalRounds,
                modifier = Modifier.size(screenWidthDp.div(1.1f))
            )
        }

        else -> {
            val screenWidthDp = configuration.screenWidthDp.dp
            _MainTimer(
                progress = progress,
                color = color,
                strokeWidth = strokeWidth,
                time = time,
                currentRound = currentRound,
                totalRounds = totalRounds,
                modifier = Modifier.size(screenWidthDp.div(0.9f))
            )
        }
    }


}

@Composable
fun _MainTimer(
    progress: Float,
    modifier: Modifier,
    color: Color,
    strokeWidth: Float = 40f,
    time: Duration,
    currentRound: Int,
    totalRounds: Int,
) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "")
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SegmentedCircle(
            totalRounds = totalRounds,
            strokeWidth = 100f,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape),
            currentRound = currentRound,
            color = color
        )
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .padding(30.dp)
        ) {
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
        Text(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = color)
                .padding(12.dp),
            text = time.toMinutesSeconds(),
            color = Color.Black,
            fontSize = 110.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun Duration.toMinutesSeconds(): String {
    val minutes = this.inWholeMinutes
    val seconds = this.inWholeSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Preview()
@Composable()
fun MainTimerPreview() {
    MainTimer(
        0.5f,
        color = Color.White,
        time = (120.toDuration(DurationUnit.SECONDS)),
        currentRound = 0,
        totalRounds = 12
    )
}