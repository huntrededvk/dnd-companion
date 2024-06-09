package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import com.khve.dndcompanion.domain.meta.usecase.DeleteMetaItemUseCase
import com.khve.dndcompanion.domain.meta.usecase.EditMetaItemUseCase
import com.khve.dndcompanion.domain.meta.usecase.GetMetaListUseCase
import javax.inject.Inject

class MetaViewModel @Inject constructor(
    private val getMetaListUseCase: GetMetaListUseCase,
    private val deleteMetaItemUseCase: DeleteMetaItemUseCase,
    private val editMetaItemUseCase: EditMetaItemUseCase,
) : ViewModel() {


}