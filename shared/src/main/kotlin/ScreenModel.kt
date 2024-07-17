import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object ScreenModel {
    val priceBTC = mutableStateOf("")
    val priceETH = mutableStateOf("")
    val priceSOL = mutableStateOf("")

    private val client = HttpClient(CIO)

    suspend fun startScope(flag: Boolean) {
//        while (!flag) {
            Log.d("PollingPrice", "12345")
            getCurrentPrice(priceBTC, "BTCUSDT")
            getCurrentPrice(priceETH, "ETHUSDT")
            getCurrentPrice(priceSOL, "SOLUSDT")
//            delay(3000)
//        }
//        priceBTC.value = "..."
//        priceETH.value = "..."
//        priceSOL.value = "..."
    }

    private fun getCurrentPrice(priceState: MutableState<String>, name: String) {
        try {
            runBlocking {
                val response =
                    client.get("https://api.bybit.com/v5/market/kline?category=linear&symbol=${name}&interval=1&limit=1")
                val data: KLine = Json.decodeFromString(response.bodyAsText())
                val isDataOk = data.toString().contains("OK")

                if (isDataOk) {
                    priceState.value = data.result.list.last()[4]
                }
            }
        } catch (_: Exception) {
            priceState.value = "..."
        }
    }
}

@Serializable
data class KLine(
    val retCode: Int,
    val retMsg: String,
    val result: KLineResult,
    val retExtInfo: Unit,
    val time: Long
)

@Serializable
data class KLineResult(val symbol: String, val category: String, val list: List<List<String>>)