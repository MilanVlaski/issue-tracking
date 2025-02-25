package com.akimi.issue_tracking.application;

import java.util.Objects;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Version {

    @Transient
    private int major;
    @Transient
    private int minor;
    @Transient
    private int patch;

    @Override
    public String toString() {
	return major + "." + minor + "." + patch;
    }

    public static Version fromString(String version) {
	String[] parts = version.split("\\.");
	return new Version(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	Version other = ((Version) obj);
	return other.major == major && other.minor == minor && other.patch == patch;
    }

    @Override
    public int hashCode() {
	return Objects.hash(major, minor, patch);
    }
}

@Converter(autoApply = true)
class VersionConverter implements AttributeConverter<Version, String> {
    @Override
    public String convertToDatabaseColumn(Version version) {
	return version != null ? version.toString() : null;
    }

    @Override
    public Version convertToEntityAttribute(String dbData) {
	return dbData != null ? Version.fromString(dbData) : null;
    }
}