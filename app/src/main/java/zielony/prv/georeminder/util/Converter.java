package zielony.prv.georeminder.util;

public interface Converter<SourceType, TargetType> {

    TargetType convert(SourceType source);
}
