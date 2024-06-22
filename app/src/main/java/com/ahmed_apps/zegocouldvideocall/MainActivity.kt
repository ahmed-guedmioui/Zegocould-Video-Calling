package com.ahmed_apps.zegocouldvideocall

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.ahmed_apps.zegocouldvideocall.ui.theme.ZegocouldVideoCallTheme
import com.permissionx.guolindev.PermissionX
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser


class MainActivity : FragmentActivity() {

    val caller = "two"
    val receiver = "one"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions {
                init()
            }
        } else {
            init()
        }

        setContent {
            ZegocouldVideoCallTheme {
                Screen()
            }
        }
    }

    @Composable
    fun Screen(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Hello $caller",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You can call $receiver",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )


            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                modifier = Modifier.size(50.dp),
                factory = {
                    ZegoSendCallInvitationButton(it).apply {
                        setIsVideoCall(true)
                        resourceID = "zego_uikit_call"
                        setInvitees(
                            listOf(
                                ZegoUIKitUser(receiver, receiver)
                            )
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                modifier = Modifier.size(50.dp),
                factory = {
                    ZegoSendCallInvitationButton(it).apply {
                        setIsVideoCall(false)
                        resourceID = "zego_uikit_call"
                        setInvitees(
                            listOf(
                                ZegoUIKitUser(receiver, receiver)
                            )
                        )
                    }
                }
            )

        }
    }

    private fun init() {
        val appID = 355776708L
        val appSign = "f18b13aa408b4d066b372656a1bd081dcc2de463f7711481765e6cc4cbbc99d2"
        val userID = caller
        val userName = caller

        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()

        ZegoUIKitPrebuiltCallService.init(
            application, appID, appSign, userID, userName, callInvitationConfig
        )
    }

    private fun permissions(
        onGranted: () -> Unit,
    ) {
        PermissionX.init(this).permissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
            .onExplainRequestReason { scope, deniedList ->
                val message =
                    "We need your consent for the following " +
                            "permissions in order to use the offline call function properly"
                scope.showRequestReasonDialog(
                    deniedList, message, "Allow", "Deny"
                )
            }.request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    onGranted()
                }
            }

    }
}

















