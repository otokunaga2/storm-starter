package storm.starter.spout;

import java.io.Serializable;

public class DiagnosisEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	public double lat;
	public double lng;
	public long time;
	public String diagnosisCode;
	public DiagnosisEvent(double lat, double lng, long time, String diag) {
		// TODO Auto-generated constructor stub
		super();
		this.time = time;
		this.lat = lat;
		this.lng = lng;
		this.diagnosisCode = diag;
	}

}
