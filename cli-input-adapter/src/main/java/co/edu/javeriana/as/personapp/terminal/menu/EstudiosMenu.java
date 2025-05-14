package co.edu.javeriana.as.personapp.terminal.menu;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.EstudiosInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.model.StudyModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EstudiosMenu {

    private static String DATABASE = "MARIA";
    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR = 2;
    private static final int OPCION_ACTUALIZAR = 3;
    private static final int OPCION_BUSCAR = 4;
    private static final int OPCION_ELIMINAR = 5;

    public void iniciarMenu(EstudiosInputAdapterCli estudiosInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuMotorPersistencia();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case PERSISTENCIA_MARIADB:
                        DATABASE = "MARIA";
                        estudiosInputAdapterCli.setStudyOutputPortInjection("MARIA");
                        menuOpciones(estudiosInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        DATABASE = "MONGO";
                        estudiosInputAdapterCli.setStudyOutputPortInjection("MONGO");
                        menuOpciones(estudiosInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    // TODO: FALTA ESTO
    private void menuOpciones(EstudiosInputAdapterCli estudiosInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuOpciones();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                        isValid = true;
                        break;
                    case OPCION_VER_TODO:
                        estudiosInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR:
                        estudiosInputAdapterCli.crearEstudios(leerEntidad(keyboard), DATABASE);
                        break;
                    case OPCION_ACTUALIZAR:
                        estudiosInputAdapterCli.editarEstudio(leerEntidad(keyboard), DATABASE);
                        break;
                    case OPCION_BUSCAR:
                        estudiosInputAdapterCli.buscarEstudio(leerIdProfesion(keyboard), leerIdPersona(keyboard),
                                DATABASE);
                        break;
                    case OPCION_ELIMINAR:
                        estudiosInputAdapterCli.eliminarEstudio(leerIdProfesion(keyboard), leerIdPersona(keyboard),
                                DATABASE);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void mostrarMenuOpciones() {
        System.out.println("\n=================================");
        System.out.println("       MENÚ DE ESTUDIOS");
        System.out.println("=================================");
        System.out.println("| " + OPCION_VER_TODO + " - Ver todos los estudios");
        System.out.println("| " + OPCION_CREAR + " - Crear un nuevo estudio");
        System.out.println("| " + OPCION_ACTUALIZAR + " - Actualizar un estudio");
        System.out.println("| " + OPCION_BUSCAR + " - Buscar estudio por ID");
        System.out.println("| " + OPCION_ELIMINAR + " - Eliminar estudio");
        System.out.println("| " + OPCION_REGRESAR_MOTOR_PERSISTENCIA + " - Volver al motor de persistencia");
        System.out.println("=================================\n");
    }

    private void mostrarMenuMotorPersistencia() {
        System.out.println("\n=================================");
        System.out.println("  SELECCIÓN DE MOTOR DE DATOS");
        System.out.println("=================================");
        System.out.println("| " + PERSISTENCIA_MARIADB + " - MariaDB");
        System.out.println("| " + PERSISTENCIA_MONGODB + " - MongoDB");
        System.out.println("| " + OPCION_REGRESAR_MODULOS + " - Regresar al módulo principal");
        System.out.println("=================================\n");
    }

    private int leerOpcion(Scanner keyboard) {
        try {
            System.out.print("🔸 Ingrese una opción: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("⚠️ Solo se permiten números.");
            keyboard.nextLine(); // limpia el buffer
            return leerOpcion(keyboard);
        }
    }

    public StudyModelCli leerEntidad(Scanner keyboard) {
        try {
            StudyModelCli model = new StudyModelCli();
            keyboard.nextLine(); // limpiar buffer

            System.out.println("\n📘 FORMULARIO DE ESTUDIO");
            System.out.println("==============================");
            System.out.print("🧍 Ingrese la identificación de la persona: ");
            model.setIdPerson(keyboard.nextLine());

            System.out.print("🎓 Ingrese la identificación de la profesión: ");
            model.setIdProfession(keyboard.nextLine());

            System.out.print("🏫 Ingrese el nombre de la universidad: ");
            model.setUniversityName(keyboard.nextLine());

            model.setGraduationDate(leerFecha(keyboard));
            System.out.println("==============================\n");

            return model;
        } catch (Exception e) {
            System.out.println("⚠️  Datos incorrectos, intenta nuevamente.");
            return leerEntidad(keyboard);
        }
    }

    private LocalDate leerFecha(Scanner keyboard) {
        try {
            System.out.print("📅 Ingrese la fecha de graduación (yyyy-mm-dd): ");
            return LocalDate.parse(keyboard.nextLine());
        } catch (Exception e) {
            System.out.println("❌ Fecha incorrecta. Intenta nuevamente.");
            return leerFecha(keyboard);
        }
    }

    private Integer leerIdProfesion(Scanner keyboard) {
        try {
            System.out.print("🎓 Ingrese el ID de la profesión: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("⚠️ Solo se permiten números.");
            keyboard.nextLine(); // limpiar buffer
            return leerIdProfesion(keyboard);
        }
    }

    private Integer leerIdPersona(Scanner keyboard) {
        try {
            System.out.print("🧍 Ingrese el ID de la persona: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("⚠️ Solo se permiten números.");
            keyboard.nextLine(); // limpiar buffer
            return leerIdPersona(keyboard);
        }
    }

}
