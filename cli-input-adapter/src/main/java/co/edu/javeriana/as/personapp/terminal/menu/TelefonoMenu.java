package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.model.PhoneModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelefonoMenu {

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

    public void iniciarMenu(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
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
                        telefonoInputAdapterCli.setPhoneOutputPortInjection("MARIA");
                        menuOpciones(telefonoInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        DATABASE = "MONGO";
                        telefonoInputAdapterCli.setPhoneOutputPortInjection("MONGO");
                        menuOpciones(telefonoInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
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
                        telefonoInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR:
                        telefonoInputAdapterCli.crearTelefono(leerEntidad(keyboard), DATABASE);
                        break;
                    case OPCION_ACTUALIZAR:
                        telefonoInputAdapterCli.editarTelefono(leerEntidad(keyboard), DATABASE);
                        break;
                    case OPCION_BUSCAR:
                        telefonoInputAdapterCli.buscarTelefono(leerNumero(keyboard), DATABASE);
                        break;
                    case OPCION_ELIMINAR:
                        telefonoInputAdapterCli.eliminarTelefono(leerNumero(keyboard), DATABASE);
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
        System.out.println("        MENÚ DE TELÉFONOS");
        System.out.println("=================================");
        System.out.println("| " + OPCION_VER_TODO + " - Ver todos los teléfonos");
        System.out.println("| " + OPCION_CREAR + " - Crear un teléfono");
        System.out.println("| " + OPCION_ACTUALIZAR + " - Actualizar un teléfono");
        System.out.println("| " + OPCION_BUSCAR + " - Buscar teléfono por número");
        System.out.println("| " + OPCION_ELIMINAR + " - Eliminar teléfono por número");
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
            keyboard.nextLine(); // Limpiar buffer
            return leerOpcion(keyboard);
        }
    }

    public PhoneModelCli leerEntidad(Scanner keyboard) {
        try {
            PhoneModelCli telefonoModelCli = new PhoneModelCli();
            keyboard.nextLine(); // Limpiar buffer

            System.out.println("\n📘 FORMULARIO DE TELÉFONO");
            System.out.println("==============================");

            System.out.print("📞 Número: ");
            telefonoModelCli.setNumber(keyboard.nextLine());

            System.out.print("📡 Compañía telefónica: ");
            telefonoModelCli.setCompany(keyboard.nextLine());

            System.out.print("🧍 ID de la persona dueña: ");
            telefonoModelCli.setIdPerson(keyboard.nextLine());

            System.out.println("==============================\n");
            return telefonoModelCli;

        } catch (Exception e) {
            System.out.println("❌ Datos incorrectos. Intenta nuevamente.\n");
            return leerEntidad(keyboard);
        }
    }

    private int leerNumero(Scanner keyboard) {
        try {
            System.out.print("📞 Ingrese el número de teléfono: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("⚠️ Solo se permiten números.");
            keyboard.nextLine(); // Limpiar buffer
            return leerNumero(keyboard);
        }
    }

}
