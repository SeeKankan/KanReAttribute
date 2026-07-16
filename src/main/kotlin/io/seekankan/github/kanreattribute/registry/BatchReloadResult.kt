package io.seekankan.github.kanreattribute.registry

//sealed class BatchReloadResult {
//    object Success: BatchReloadResult()
//    sealed class Failed(val reason: String): BatchReloadResult() {
//        data class ExecutionError(val exceptionMap: Map<out String, out Throwable>): Failed(
//            "Exception(s) happen on reload: ${exceptionMap.map { (key, ex) ->
//                "Key $key throw ${ex.toString()}"
//            }.joinToString()}"
//        )
//    }
//}
data class BatchReloadResult(val results: List<ReloadResult>) {
    val successCount: Int
    val failureResults: List<ReloadResult.Failure>
    val executionResults: List<ReloadResult.Failure.ExecutionError>

    init {
        var successNumber = 0
        val failureResultList = mutableListOf<ReloadResult.Failure>()
        val executionResultList = mutableListOf<ReloadResult.Failure.ExecutionError>()

        results.forEach { result ->
            when(result) {
                is ReloadResult.Failure.ExecutionError -> {
                    failureResultList.add(result)
                    executionResultList.add(result)
                }
                ReloadResult.Success -> {
                    successNumber++
                }
            }
        }
        successCount = successNumber
        failureResults = failureResultList
        executionResults = executionResultList
    }
}