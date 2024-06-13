import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import moneytracker.composeapp.generated.resources.Res
import moneytracker.composeapp.generated.resources.compose_multiplatform
import java.io.File

@Composable
fun AccountBalance(accountName: String, balance: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(accountName)
        Text(balance)
    }
}
@Composable
fun Balance() {
    Column(modifier = Modifier.width(300.dp)) {
        AccountBalance("Total", "$2.000,21")
        AccountBalance("Total", "$2.000,21")
        AccountBalance("Total", "$2.000,21")
    }
}
@Composable
@Preview
fun App() {

    val workingDirectory = System.getProperty("user.dir")
    val scriptLocation = File("$workingDirectory/money_tracker_cli")
    val mt = MoneyTrackerCLI(scriptLocation, "sqlite::test.db")

    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Balance()
        }
    }
}