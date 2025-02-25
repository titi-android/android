package core.designsystem.svg

import androidx.compose.ui.graphics.vector.ImageVector
import core.designsystem.svg.myiconpack.IcBus
import core.designsystem.svg.myiconpack.IcCancel
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.svg.myiconpack.IcEdit
import core.designsystem.svg.myiconpack.IcForwardArrow
import core.designsystem.svg.myiconpack.IcInfomationCircle
import core.designsystem.svg.myiconpack.IcMainlogo
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcOffCheckbox
import core.designsystem.svg.myiconpack.IcOffnotify
import core.designsystem.svg.myiconpack.IcOnCheckbox
import core.designsystem.svg.myiconpack.IcRefresh
import core.designsystem.svg.myiconpack.IcSearch
import core.designsystem.svg.myiconpack.IcSetting
import core.designsystem.svg.myiconpack.IcTalk
import core.designsystem.svg.myiconpack.ImageBusOfTicket
import kotlin.collections.List as ____KtList

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(IcNotify, IcTalk, IcMainlogo, IcClose, IcOffnotify, IcSetting, IcRefresh,
        IcOnCheckbox, IcInfomationCircle, ImageBusOfTicket, IcOffCheckbox, IcSearch, IcBus, IcEdit,
        IcCancel, IcForwardArrow)
    return __AllIcons!!
  }
