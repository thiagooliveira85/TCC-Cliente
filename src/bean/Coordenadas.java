package bean;

import org.primefaces.model.map.LatLng;

/**
 * Classe que representa as coordenadas latitude e longitude utilizadas na API do Google
 * @author Thiago
 *
 */
public class Coordenadas {
	
	private String latitude;
	private String longitude;
	
	private LatLng latLong;

	public Coordenadas(String latitude, String longitude) {
		
		if ( latitude == null || longitude == null || latitude.equals("") || longitude.equals(""))
			throw new IllegalArgumentException("Não foi possível setar as coordenadas");
		
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	@Override
	public String toString() {
		return latitude + "," + longitude;
	}

	public LatLng getLatLng() {
		return new LatLng(new Double(latitude), new Double(longitude));
	}

	public LatLng getLatLong() {
		return latLong;
	}

	public void setLatLong(LatLng latLong) {
		this.latLong = latLong;
	}
}
