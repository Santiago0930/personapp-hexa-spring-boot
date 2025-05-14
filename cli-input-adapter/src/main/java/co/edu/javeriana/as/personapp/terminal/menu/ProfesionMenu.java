package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.ProfesionInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfessionModelCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class ProfesionMenu {

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

    public void iniciarMenu(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
                        ProfesionMenu.DATABASE = "MARIA";
                        profesionInputAdapterCli.setProfessionOutputPortInjection(ProfesionMenu.DATABASE);
                        menuOpciones(profesionInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        ProfesionMenu.DATABASE = "MONGO";
                        profesionInputAdapterCli.setProfessionOutputPortInjection(ProfesionMenu.DATABASE);
                        menuOpciones(profesionInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
                        profesionInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR:
                        profesionInputAdapterCli.crearProfesion(leerEntidad(keyboard), ProfesionMenu.DATABASE);
                        break;
                    case OPCION_ACTUALIZAR:
                        profesionInputAdapterCli.editarProfesion(leerEntidad(keyboard), ProfesionMenu.DATABASE);
                        break;
                    case OPCION_BUSCAR:
                        profesionInputAdapterCli.buscarProfesion(ProfesionMenu.DATABASE, leerIdentificacion(keyboard));
                        break;
                    case OPCION_ELIMINAR:
                        profesionInputAdapterCli.eliminarProfesion(ProfesionMenu.DATABASE,
                                leerIdentificacion(keyboard));
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
        System.out.println("     MENÚ DE PROFESIONES");
        System.out.println("=================================");
        System.out.println("| " + OPCION_VER_TODO + " - Ver todas las profesiones");
        System.out.println("| " + OPCION_CREAR + " - Crear una profesión");
        System.out.println("| " + OPCION_ACTUALIZAR + " - Actualizar una profesión");
        System.out.println("| " + OPCION_BUSCAR + " - Buscar profesión por ID");
        System.out.println("| " + OPCION_ELIMINAR + " - Eliminar profesión por ID");
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
            keyboard.nextLine(); // limpiar buffer
            return leerOpcion(keyboard);
        }
    }

    private int leerIdentificacion(Scanner keyboard) {
        try {
            System.out.print("🆔 Ingrese el ID de la profesión: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("⚠️ Solo se permiten números.");
            keyboard.nextLine(); // limpiar buffer
            return leerIdentificacion(keyboard);
        }
    }

    public ProfessionModelCli leerEntidad(Scanner keyboard) {
        try {
            ProfessionModelCli profesion = new ProfessionModelCli();
            System.out.println("\n📘 FORMULARIO DE PROFESIÓN");
            System.out.println("==============================");

            System.out.print("🆔 ID de la profesión: ");
            profesion.setId(keyboard.nextInt());
            keyboard.nextLine();

            System.out.print("📛 Nombre: ");
            profesion.setName(keyboard.nextLine());

            System.out.print("📝 Descripción: ");
            profesion.setDescription(keyboard.nextLine());

            System.out.println("==============================\n");
            return profesion;

        } catch (InputMismatchException e) {
            System.out.println("❌ Datos incorrectos. Intenta nuevamente.\n");
            keyboard.nextLine(); // limpiar buffer
            return leerEntidad(keyboard);
        }
    }

}