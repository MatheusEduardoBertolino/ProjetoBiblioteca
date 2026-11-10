package br.edu.eseg.biblioteca.model.converter;

import java.time.LocalDate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * O SQLite não tem tipo DATE: as datas ficam gravadas como TEXT no formato AAAA-MM-DD.
 * Este converter traduz entre o texto do banco e o LocalDate do Java.
 */
@Converter
public class DataTextoConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public String convertToDatabaseColumn(LocalDate data) {
        return data == null ? null : data.toString();
    }

    @Override
    public LocalDate convertToEntityAttribute(String texto) {
        return texto == null ? null : LocalDate.parse(texto);
    }
}
