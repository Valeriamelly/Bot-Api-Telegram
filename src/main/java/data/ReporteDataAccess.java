package data;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import model.Reporte;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ReporteDataAccess {

    private Sheets sheetsService;
    private String spreadsheetId;

    public ReporteDataAccess(Sheets sheetsService, String spreadsheetId) {
        this.sheetsService = sheetsService;
        this.spreadsheetId = spreadsheetId;
    }

    public List<Reporte> obtenerReportesDesdeRango(String rango) throws IOException {
        List<Reporte> reportes = new ArrayList<>();
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, rango).execute();
        List<List<Object>> values = response.getValues();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

        if (values == null || values.isEmpty()) {
            System.out.println("No se encontraron datos.");
        } else {
            for (List<Object> row : values) {
                Reporte reporte = new Reporte();
                reporte.setId(!row.isEmpty() ? row.get(0).toString() : "");
                if (row.size() > 1 && !row.get(1).toString().equalsIgnoreCase("Fecha Registro")) {
                    try {
                        LocalDate fecha = LocalDate.parse(row.get(1).toString(), formatter);
                        reporte.setFechaReporte(fecha);
                    } catch (DateTimeParseException e) {
                        System.err.println("Error al parsear la fecha: " + row.get(1).toString());
                        // Aquí puedes manejar el error, por ejemplo, asignando null a fechaReporte
                        reporte.setFechaReporte(null);
                    }
                }
                reporte.setTipoReporte(row.size() > 2 ? row.get(2).toString() : "");
                reporte.setModulo(row.size() > 3 ? row.get(3).toString() : "");
                reporte.setComponente(row.size() > 4 ? row.get(4).toString() : "");
                reporte.setAccion(row.size() > 5 ? row.get(5).toString() : "");
                reporte.setObservacion(row.size() > 6 ? row.get(6).toString() : "");
                reporte.setSolucion(row.size() > 7 ? row.get(7).toString() : "");
                reporte.setPrioridad(row.size() > 8 ? row.get(8).toString() : "");
                reporte.setNorma(row.size() > 9 ? row.get(9).toString() : "");
                reportes.add(reporte);
            }
        }

        return reportes;
    }
}
