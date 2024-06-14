import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

import java.io.File

class BalanceViewModel(val mt: MoneyTrackerCLI) : ViewModel() {
    private val _accounts = MutableStateFlow(listOf("Blue", "Red", "Yellow"))
    val accounts: StateFlow<List<String>> = _accounts.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            mt.accounts.list()
            _accounts.value = listOf("valid accounts")
        }
    }
}

@Composable
fun AccountBalance(accountName: String, balance: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(accountName)
        Text(balance)
    }
}
@Composable
fun Balance(mt: MoneyTrackerCLI) {
    val viewModel: BalanceViewModel = viewModel { BalanceViewModel(mt) }
    val accounts = viewModel.accounts.value
    Column(modifier = Modifier.width(300.dp)) {
        accounts.map {
            AccountBalance(it, "$2.000,21")
        }

//        AccountBalance("Total", "$2.000,21")
//        AccountBalance("Total", "$2.000,21")
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
            Balance(mt)
        }
    }
}