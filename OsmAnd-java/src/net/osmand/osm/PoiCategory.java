package net.osmand.osm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PoiCategory extends PoiFilter {
	public static final PoiCategoryComparator COMPARATOR = new PoiCategoryComparator();

	private List<PoiFilter> poiFilters = new ArrayList<PoiFilter>();
	private Set<PoiType> basemapPoi = null;
	private int regId;
	private String defaultTag;

	public PoiCategory(MapPoiTypes registry, String keyName, int regId) {
		super(registry, null, keyName);
		this.regId = regId;
	}

	public void addPoiType(PoiFilter poi) {
		poiFilters.add(poi);
	}

	public List<PoiFilter> getPoiFilters() {
		return poiFilters;
	}
	
	public String getDefaultTag() {
		if(defaultTag == null) {
			return keyName; }
		return defaultTag;
	}
	
	public void setDefaultTag(String defaultTag) {
		this.defaultTag = defaultTag;
	}
	
	public Map<PoiCategory, LinkedHashSet<String>> putTypes(
			Map<PoiCategory, LinkedHashSet<String>> acceptedTypes) {
		acceptedTypes.put(this, null);
		addReferenceTypes(acceptedTypes);
		return acceptedTypes;
	}

	
	public boolean isWiki() {
		return keyName.equals(MapPoiTypes.OSM_WIKI_CATEGORY);
	}


	public int ordinal() {
		return regId;
	}

	
	public void addBasemapPoi(PoiType pt) {
		if(basemapPoi == null) {
			basemapPoi = new HashSet<PoiType>();
		}
		basemapPoi.add(pt);
	}

	public boolean containsBasemapPoi(PoiType pt) {
		if(basemapPoi == null) {
			return false;
		}
		return basemapPoi.contains(pt);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PoiCategory)) {
			return false;
		}
		PoiCategory other = (PoiCategory) o;
		return regId == other.regId;
	}

	@Override
	public int hashCode() {
		return regId;
	}

	@Override
	public String toString() {
		return keyName + " (" + regId + ")";
	}

	public static class PoiCategoryComparator implements Comparator<PoiCategory> {
		@Override
		public int compare(PoiCategory c1, PoiCategory c2) {
			if (c1 == null ^ c2 == null) {
				return (c1 == null) ? -1 : 1;
			} else if (c1 == c2) {
				return 0;
			} else {
				return Double.compare(c1.regId, c2.regId);
			}
		}
	}

}
