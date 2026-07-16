package io.seekankan.github.kanreattribute.registry

data class BatchUnregisterResult(val results: List<UnregisterResult>) {
    val successCount: Int
    val failureResults: List<UnregisterResult.Failure>
    val notFoundResults: List<UnregisterResult.Failure.NotFound>
    val executionResults: List<UnregisterResult.Failure.ExecutionError>

    init {
        var successNumber = 0
        val failureResultList = mutableListOf<UnregisterResult.Failure>()
        val notFoundResultList = mutableListOf<UnregisterResult.Failure.NotFound>()
        val executionResultList = mutableListOf<UnregisterResult.Failure.ExecutionError>()

        results.forEach { result ->
            when(result) {
                is UnregisterResult.Failure.NotFound -> {
                    failureResultList.add(result)
                    notFoundResultList.add(result)
                }
                is UnregisterResult.Failure.ExecutionError -> {
                    failureResultList.add(result)
                    executionResultList.add(result)
                }
                UnregisterResult.Success -> {
                    successNumber++
                }
            }
        }
        successCount = successNumber
        failureResults = failureResultList
        notFoundResults = notFoundResultList
        executionResults = executionResultList
    }
}