package cz.fit.metacentrum.domain.config

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName


const val taskTypeMatlabType = "MATLAB"

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(value = MatlabTaskType::class, name = taskTypeMatlabType)
)
sealed class TaskType()

@JsonTypeName(taskTypeMatlabType)
data class MatlabTaskType(
        val matlabDir: String,
        val functionName: String,
        val parameters: List<String>
) : TaskType()