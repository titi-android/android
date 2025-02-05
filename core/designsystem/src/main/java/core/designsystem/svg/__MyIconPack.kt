package core.designsystem.svg

import androidx.compose.ui.graphics.vector.ImageVector
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.svg.myiconpack.IcEdit
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcNotnotify
import core.designsystem.svg.myiconpack.IcSetting
import core.designsystem.svg.myiconpack.ImageBusOfTicket
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(IcNotify, IcNotnotify, IcClose, IcSetting, ImageBusOfTicket, IcEdit)
    return __AllIcons!!
  }
