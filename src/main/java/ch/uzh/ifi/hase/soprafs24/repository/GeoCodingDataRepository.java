package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class GeoCodingDataRepository {
  private final Map<Long, GeoCodingData> geoCodingDataRepositoryById;
  private final List<String> locations;

  private static GeoCodingDataRepository INSTANCE = new GeoCodingDataRepository();

  private GeoCodingDataRepository() {
    geoCodingDataRepositoryById = new HashMap<>();
    locations = new ArrayList<>();
  }

  public static void addGeoCodingData(GeoCodingData geoCodingData) {
    INSTANCE.geoCodingDataRepositoryById.put(geoCodingData.getId(), geoCodingData);
    INSTANCE.locations.add(geoCodingData.getLocation());
  }

  public static GeoCodingData getGeoCodingDataById(Long geoCodingDataId) {
    return INSTANCE.geoCodingDataRepositoryById.get(geoCodingDataId);
  }

  public static GeoCodingData geoCodingDataByLocation(String locationName) {
    for (GeoCodingData geoCodingData : INSTANCE.geoCodingDataRepositoryById.values()) {
      if (geoCodingData.getLocation().equals(locationName)) {
          return geoCodingData;
      }
    }
    return null;
  }

  public static List<GeoCodingData> findAll() {
    return new ArrayList<>(INSTANCE.geoCodingDataRepositoryById.values());
  }
}
