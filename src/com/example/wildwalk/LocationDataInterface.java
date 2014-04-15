package com.example.wildwalk;

import com.google.android.gms.common.ConnectionResult;

public interface LocationDataInterface {
	public void disconnected();
	public void connectionFailed(ConnectionResult co);
}
