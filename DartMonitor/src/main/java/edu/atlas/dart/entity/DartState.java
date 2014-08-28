package edu.atlas.dart.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Calendar;

@ToString
@RequiredArgsConstructor
public class DartState {

    @Getter private final Calendar date;
    @Getter private final double height;

}
