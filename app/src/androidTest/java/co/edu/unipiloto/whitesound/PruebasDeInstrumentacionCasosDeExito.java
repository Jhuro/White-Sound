package co.edu.unipiloto.whitesound;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.unipiloto.whitesound.actividades.HomeActivity;

@RunWith(AndroidJUnit4.class)
public class PruebasDeInstrumentacionCasosDeExito {

    int centerX = 0;
    int centerY = 0;

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    @Test
    public void cp01_seleccionarPartituraEnPartiturasAnteriores() {

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Seleccionar partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));

        //Prueba de HU-01 - Seleccionar partitura
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).check(matches(isDisplayed()));
        onView(withId(R.id.pop_imgbtn_lectura_partitura)).check(matches(isDisplayed()));
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).check(matches(isDisplayed()));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        scenario.close();
    }

    @Test
    public void cp02_añadirNotaEnPartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Agregar nota"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Agregar nota")));

        //Prueba de HU-02 - Añadir Nota
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp03_aumentarAlturaDeNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Aumentar altura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Aumentar altura")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Negra")));

        //Prueba de HU-03 - Aumentar altura de nota
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Re Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp04_disminuirAlturaDeNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Disminuir altura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Disminuir altura")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Re Negra")));

        onView(withId(R.id.fe_imgbtn_bajar_altura)).check((view, noViewFoundException) -> {
            int[] xy = new int[2];
            view.getLocationOnScreen(xy);
            int width = view.getWidth();
            int height = view.getHeight();
            setCenterX(xy[0] + width / 2 - 200);
            setCenterY(xy[1] + height / 2 - 200);
        });

        //Prueba de HU-04 - Disminuir altura de nota
        onView(withId(R.id.fe_imgbtn_bajar_altura)).perform(
                new GeneralClickAction(
                        Tap.SINGLE,
                        new CoordinatesProvider() {
                            @Override
                            public float[] calculateCoordinates(View view) {
                                return new float[]{(float) centerX, (float) centerY};
                            }
                        },
                        Press.FINGER
                )
        );

        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp05_cambiarDuracionDeNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Cambiar duracion"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Cambiar duracion")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Negra")));

        //Prueba de HU-05 - Modificar duración de notas
        onView(withId(R.id.miep_figuras_musicales)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Redonda")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp06_crearPartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Prueba de HU-06 - Crear partitura
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Nueva partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Nueva partitura")));
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).check(matches(isDisplayed()));

        //Limpiar ambiente de pruebas
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp07_eliminarPartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Eliminar partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Eliminar partitura")));
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));

        //Prueba de HU-07 - Eliminar partitura
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(not(isDisplayed())));

        scenario.close();
    }

    @Test
    public void cp08_abrirEditorDePartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Editor de partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Editor de partitura")));
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));

        //Prueba de HU-08 - Abrir editor de partitura
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Editor de partitura")));
        onView(withId(R.id.fe_et_autor)).check(matches(withText("Test")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp09_abrirPanelDeFigurasMusicales() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Panel de figuras"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Panel de figuras")));

        //Prueba de HU-09 - Abrir panel de figuras musicales
        onView(withId(R.id.miep_figuras_musicales)).perform(click());
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Redonda"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Blanca"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Negra"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Corchea"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Semicorchea"))));
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp10_abrirPanelDeSilencios() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Panel de silencios"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Panel de silencios")));

        //Prueba de HU-10 - Abrir panel de silencios
        onView(withId(R.id.miep_silencios)).perform(click());
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Silencio de redonda"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Silencio de blanca"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Silencio de negra"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Silencio de corchea"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Silencio de semicorchea"))));
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp11_abrirPanelDeAlteraciones() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Panel de alteraciones"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Panel de alteraciones")));

        //Prueba de HU-11 - Abrir panel de alteraciones
        onView(withId(R.id.miep_alteraciones)).perform(click());
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Sostenido"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Bemol"))));
        onView(withId(R.id.pee_lv_elementos)).check(matches(hasDescendant(withText("Sin alteración"))));
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp12_abrirInterfazDeAjustes() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Abrir ajustes"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Abrir ajustes")));

        //Prueba de HU-12 - Abrir ajustes de la aplicación
        onView(withId(R.id.mt_ajustes)).perform(click());
        onView(withId(R.id.aa_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.mt_volver)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.mt_ajustes)).perform(click());
        onView(withId(R.id.aa_toolbar)).check(matches(isDisplayed()));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_volver)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp13_activarSolfeo() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Activar solfeo"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Activar solfeo")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.mt_ajustes)).perform(click());
        onView(withId(R.id.aa_toolbar)).check(matches(isDisplayed()));

        //Prueba de HU-13 - Activar solfeo
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());
        onView(withId(R.id.mt_volver)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.mt_ajustes)).perform(click());
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_volver)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp14_cambiarTituloDePartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Cambiar titulo"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Cambiar titulo")));

        //Prueba de HU-14 - Cambiar título de la partitura
        onView(withId(R.id.fe_et_titulo)).perform(clearText());
        onView(withId(R.id.fe_et_titulo)).perform(typeText("Nuevo titulo"), closeSoftKeyboard());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Nuevo titulo")));
        onView(withId(R.id.mt_guardar)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Nuevo titulo")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp15_cambiarAutorDePartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Cambiar autor"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Cambiar autor")));

        //Prueba de HU-15 - Cambiar autor de la partitura
        onView(withId(R.id.fe_et_autor)).perform(clearText());
        onView(withId(R.id.fe_et_autor)).perform(typeText("Nuevo autor"), closeSoftKeyboard());
        onView(withId(R.id.fe_et_autor)).check(matches(withText("Nuevo autor")));
        onView(withId(R.id.mt_guardar)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).perform(click());
        onView(withId(R.id.fe_et_autor)).check(matches(withText("Nuevo autor")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp16_guardarCambiosEnPartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Guardar partiura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Guardar partiura")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());

        //Prueba de HU-16 - Guardar cambios de edición
        onView(withId(R.id.mt_guardar)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Guardar partiura")));
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp17_agregarAlteracion() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Agregar alteracion"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Agregar alteracion")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());

        //Prueba de HU-17 - Agregar alteración a una nota
        onView(withId(R.id.miep_alteraciones)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Sostenido Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp18_eliminarNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Eliminar nota"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Eliminar nota")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());

        //Prueba de HU-18 - Eliminar una nota
        onView(withId(R.id.miep_borrar)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Agregar nota")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp19_cambiarAModoLectura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Modo lectura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Modo lectura")));

        //Prueba de HU-19 - Cambiar a modo lectura
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_imgbtn_reproducir)).check(matches(isDisplayed()));
        onView(withId(R.id.fl_imgbtn_pausar)).check(matches(isDisplayed()));
        onView(withId(R.id.fl_imgbtn_reiniciar)).check(matches(isDisplayed()));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp20_reproducirPartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Reproducir partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Reproducir partitura")));

        //Prueba de HU-20 - Reproducir partitura
        onView(withId(R.id.fl_imgbtn_reproducir)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Do Negra")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Re Negra")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Mi Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp21_pausarReproduccion() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Pausar reproduccion"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Pausar reproduccion")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_imgbtn_reproducir)).perform(click());

        //Prueba de HU-21 - Pausar reproducción
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Do Negra")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.fl_imgbtn_pausar)).check((view, noViewFoundException) -> {
            int[] xy = new int[2];
            view.getLocationOnScreen(xy);
            int width = view.getWidth();
            int height = view.getHeight();
            setCenterX(xy[0] + width / 2 - 200);
            setCenterY(xy[1] + height / 2 - 200);
        });

        onView(withId(R.id.fl_imgbtn_pausar)).perform(
                new GeneralClickAction(
                        Tap.SINGLE,
                        new CoordinatesProvider() {
                            @Override
                            public float[] calculateCoordinates(View view) {
                                return new float[]{(float) centerX, (float) centerY};
                            }
                        },
                        Press.FINGER
                )
        );

        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Re Negra")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Re Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp22_reiniciarPartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Reiniciar partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Reiniciar partitura")));
        onView(withId(R.id.fl_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fl_imgbtn_desplazar_der)).perform(click());

        //Prueba de HU-22 - Reiniciar lectura de partitura
        onView(withId(R.id.fl_imgbtn_reiniciar)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Do Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp23_practicarAlturaDeNotas() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_juegos)).perform(click());

        //Prueba de HU-23 - Practicar alturas de notas
        onView(withId(R.id.fjh_btn_altura)).perform(click());
        onView(withId(R.id.fjr_imgbtn_bajar_altura)).check(matches(isDisplayed()));
        onView(withId(R.id.fjr_imgbtn_subir_altura)).check(matches(isDisplayed()));
        onView(withId(R.id.miep_alteraciones)).check(matches(isDisplayed()));
        onView(withId(R.id.miep_figuras_musicales)).check(matches(not(isDisplayed())));
        try {
            Thread.sleep(125000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.prj_tv_resultados)).perform(click());
        onView(withId(R.id.prj_tv_resultados)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void cp24_practicarDuracionDeNotas() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_juegos)).perform(click());

        //Prueba de HU-24 - Practicar duración de notas
        onView(withId(R.id.fjh_btn_duracion)).perform(click());
        onView(withId(R.id.fjr_imgbtn_bajar_altura)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fjr_imgbtn_subir_altura)).check(matches(not(isDisplayed())));
        onView(withId(R.id.miep_alteraciones)).check(matches(not(isDisplayed())));
        onView(withId(R.id.miep_figuras_musicales)).check(matches(isDisplayed()));
        try {
            Thread.sleep(125000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.prj_tv_resultados)).perform(click());
        onView(withId(R.id.prj_tv_resultados)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void cp25_practicarAlturaYDuracionDeNotas() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_juegos)).perform(click());

        //Prueba de HU-25 - Practicar altura y duración de notas
        onView(withId(R.id.fjh_btn_combinado)).perform(click());
        onView(withId(R.id.fjr_imgbtn_bajar_altura)).check(matches(isDisplayed()));
        onView(withId(R.id.fjr_imgbtn_subir_altura)).check(matches(isDisplayed()));
        onView(withId(R.id.miep_alteraciones)).check(matches(isDisplayed()));
        onView(withId(R.id.miep_figuras_musicales)).check(matches(isDisplayed()));
        try {
            Thread.sleep(125000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.prj_tv_resultados)).perform(click());
        onView(withId(R.id.prj_tv_resultados)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void cp26_desplazarseEnLaPartituraALaDerecha() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Desplazar derecha"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Desplazar derecha")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_edicion)).perform(click());

        //Prueba de HU-26 - Desplazarse en la partitura a la derecha
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Agregar nota")));
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Desplazar derecha")));
        onView(withId(R.id.fl_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Re Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        scenario.close();
    }

    @Test
    public void cp27_desplazarseEnLaPartituraALaIzquierda() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Desplazar izquierda"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Desplazar izquierda")));
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_edicion)).perform(click());

        //Prueba de HU-27 - Desplazarse en la partitura a la izquierda
        onView(withId(R.id.fe_imgbtn_desplazar_izq)).check((view, noViewFoundException) -> {
            int[] xy = new int[2];
            view.getLocationOnScreen(xy);
            int width = view.getWidth();
            int height = view.getHeight();
            setCenterX(xy[0] + width / 2 - 200);
            setCenterY(xy[1] + height / 2 - 200);
            System.out.println(centerX + " x/y " + centerY);
        });

        onView(withId(R.id.fe_imgbtn_desplazar_izq)).perform(
                new GeneralClickAction(
                        Tap.SINGLE,
                        new CoordinatesProvider() {
                            @Override
                            public float[] calculateCoordinates(View view) {
                                return new float[]{(float) centerX, (float) centerY};
                            }
                        },
                        Press.FINGER
                )
        );

        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Agregar nota")));
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Desplazar izquierda")));
        onView(withId(R.id.fl_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fl_imgbtn_desplazar_izq)).check((view, noViewFoundException) -> {
            int[] xy = new int[2];
            view.getLocationOnScreen(xy);
            int width = view.getWidth();
            int height = view.getHeight();
            setCenterX(xy[0] + width / 2 - 200);
            setCenterY(xy[1] + height / 2 - 200);
        });

        onView(withId(R.id.fl_imgbtn_desplazar_izq)).perform(
                new GeneralClickAction(
                        Tap.SINGLE,
                        new CoordinatesProvider() {
                            @Override
                            public float[] calculateCoordinates(View view) {
                                return new float[]{(float) centerX, (float) centerY};
                            }
                        },
                        Press.FINGER
                )
        );
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Do Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        scenario.close();
    }

    @Test
    public void cp28_reproducirNotaPractica() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_altura)).perform(click());

        //Prueba de HU-28 - Reproducir nota de la práctica
        onView(withId(R.id.fjr_imgbtn_repetir_nota)).perform(click());
        scenario.close();
    }

    @Test
    public void cp29_responderNotaPractica() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_altura)).perform(click());

        //Prueba de HU-29 - Responder nota de la práctica
        onView(withId(R.id.fjr_imgbtn_responder)).perform(click());
        scenario.close();
    }

    @Test
    public void cp30_repetirPractica() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_altura)).perform(click());
        try {
            Thread.sleep(125000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.prj_tv_resultados)).perform(click());

        //Prueba de HU-30 - Repetir práctica
        onView(withId(R.id.prj_btn_reiniciar)).perform(click());
        onView(withId(R.id.fjr_tv_tiempo)).check(matches(withText(not("0"))));
        scenario.close();
    }

    @Test
    public void cp31_cambiarAModoEdicion() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Modo edicion"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Modo edicion")));
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());

        //Prueba de HU-31 - Cambiar a modo edición
        onView(withId(R.id.fe_imgbtn_modo_edicion)).perform(click());
        onView(withId(R.id.fe_imgbtn_bajar_altura)).check(matches(isDisplayed()));
        onView(withId(R.id.fe_imgbtn_subir_altura)).check(matches(isDisplayed()));
        onView(withId(R.id.fe_imgbtn_desplazar_izq)).check(matches(isDisplayed()));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).check(matches(isDisplayed()));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp32_informacionDeInterfaz() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Informacion"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Informacion")));
        onView(withId(R.id.mt_inicio)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Prueba de HU-32 - Abrir información de la interfaz
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información inicio")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información juegos")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.fjh_btn_altura)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información práctica de altura")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_duracion)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información práctica de duración")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_combinado)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información práctica combinada")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información edición")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información lectura")));
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.mt_ajustes)).perform(click());
        onView(withId(R.id.mt_info)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ai_tv_titulo)).check(matches(withText("Información ajustes")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.ai_btn_atras)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp33_abrirLecturaDePartitura() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Lectura de partitura"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Lectura de partitura")));
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));

        //Prueba de HU-33 - Abrir lectura de partitura
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_lectura_partitura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Lectura de partitura")));
        onView(withId(R.id.fl_tv_autor)).check(matches(withText("Test")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp34_volverAInterfazDeInicio() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Inicio"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Inicio")));
        onView(withId(R.id.mt_inicio)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Prueba de HU-34 - Volver a la interfaz de inicio
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ah_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_altura)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ah_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_duracion)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ah_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.ah_btn_juegos)).perform(click());
        onView(withId(R.id.fjh_btn_combinado)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ah_toolbar)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_editar_partitura)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ah_toolbar)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_lectura_partitura)).perform(click());
        onView(withId(R.id.mt_inicio)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ah_toolbar)).check(matches(isDisplayed()));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp35_salirDeLaAplicacion() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Prueba de HU-35 - Salir de la aplicación
        onView(withId(R.id.mt_salir)).perform(click());
        scenario.close();
    }
}