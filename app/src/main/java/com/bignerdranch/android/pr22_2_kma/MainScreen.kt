package com.bignerdranch.android.pr22_2_kma

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(vm: RateViewModel = viewModel()) {

    val rateList by vm.rateList.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = vm.rateNameStart,
            onValueChange = { vm.changeNameStart(it) },
            label = { Text("Base currency") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = vm.rateDataStart.toString(),
            onValueChange = { vm.changeDataStart(it) },
            label = { Text("Base amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = vm.rateName,
            onValueChange = { vm.changeName(it) },
            label = { Text("Target currency 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = vm.rateData.toString(),
            onValueChange = { vm.changeData(it) },
            label = { Text("Target amount 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { vm.addRate() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить запись")
        }

        Spacer(Modifier.height(24.dp))

        Text("Сохранённые записи:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(rateList) { rate ->
                RateItem(rate = rate, onDelete = { vm.deleteRate(rate.id) })
            }
        }
    }
}

@Composable
fun RateItem(rate: Rate, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Base: ${rate.name_start} = ${rate.data_start}")
            Text("Currency 1: ${rate.name} = ${rate.data}")

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("Удалить")
            }
        }
    }
}
