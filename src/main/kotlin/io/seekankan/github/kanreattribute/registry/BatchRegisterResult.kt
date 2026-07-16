package io.seekankan.github.kanreattribute.registry

data class BatchRegisterResult(val results: List<RegisterResult>) {
    val successCount: Int
    val failureResults: List<RegisterResult.Failure>
    val duplicateResults: List<RegisterResult.Failure.Duplicate>
    val executionResults: List<RegisterResult.Failure.ExecutionError>

    init {
        var successNumber = 0
        val failureResultList = mutableListOf<RegisterResult.Failure>()
        val duplicateResultList = mutableListOf<RegisterResult.Failure.Duplicate>()
        val executionResultList = mutableListOf<RegisterResult.Failure.ExecutionError>()

        results.forEach { result ->
            when(result) {
                is RegisterResult.Failure.Duplicate -> {
                    failureResultList.add(result)
                    duplicateResultList.add(result)
                }
                is RegisterResult.Failure.ExecutionError -> {
                    failureResultList.add(result)
                    executionResultList.add(result)
                }
                RegisterResult.Success -> {
                    successNumber++
                }
            }
        }
        successCount = successNumber
        failureResults = failureResultList
        duplicateResults = duplicateResultList
        executionResults = executionResultList
    }
}