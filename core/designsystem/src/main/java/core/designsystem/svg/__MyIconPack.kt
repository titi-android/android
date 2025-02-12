package core.designsystem.svg

import androidx.compose.ui.graphics.vector.ImageVector
import core.designsystem.svg.myiconpack.IcBus
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.svg.myiconpack.IcEdit
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcOffnotify
import core.designsystem.svg.myiconpack.IcRefresh
import core.designsystem.svg.myiconpack.IcSetting
import core.designsystem.svg.myiconpack.IcTalk
import core.designsystem.svg.myiconpack.ImageBusOfTicket
import kotlin.collections.List as ____KtList

public object IconPack

private var _AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (_AllIcons != null) {
      return _AllIcons!!
    }
    _AllIcons = listOf(IcNotify, IcTalk, IcOffnotify, IcClose, IcSetting, IcRefresh,
        ImageBusOfTicket, IcBus, IcEdit)
    return _AllIcons!!
  }
