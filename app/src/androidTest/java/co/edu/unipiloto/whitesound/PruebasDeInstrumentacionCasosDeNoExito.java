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
public class PruebasDeInstrumentacionCasosDeNoExito {

    int centerX = 0;
    int centerY = 0;

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    @Test
    public void cp36_aumentarAlturaDeNotaEnSi() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Aumentar altura en Si"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Aumentar altura en Si")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Si Negra")));

        //Prueba de HU-03 - Aumentar altura de nota - Caso de no éxito
        onView(withId(R.id.fe_imgbtn_subir_altura)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Si Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp37_disminuirAlturaDeNotaEnDo() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Disminuir altura en Do"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Disminuir altura en Do")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Do Negra")));

        onView(withId(R.id.fe_imgbtn_bajar_altura)).check((view, noViewFoundException) -> {
            int[] xy = new int[2];
            view.getLocationOnScreen(xy);
            int width = view.getWidth();
            int height = view.getHeight();
            setCenterX(xy[0] + width / 2 - 200);
            setCenterY(xy[1] + height / 2 - 200);
        });

        //Prueba de HU-04 - Disminuir altura de nota - Caso de no éxito
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
    public void cp38_agregarAlteracionEnNotaErronea() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Agregar alteracion en nota erronea"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Agregar alteracion en nota erronea")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());

        //Prueba de HU-17 - Agregar alteración a una nota - Caso de no éxito
        onView(withId(R.id.miep_alteraciones)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(1).perform(click());
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
    public void cp39_agregarAlteracionEnPosicionSinNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Agregar alteracion en posicion vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Agregar alteracion en posicion vacia")));

        //Prueba de HU-17 - Agregar alteración a una nota - Caso de no éxito
        onView(withId(R.id.miep_alteraciones)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());
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
    public void cp40_agregarAlteracionEnSilencio() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Agregar alteracion en posicion vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Agregar alteracion en posicion vacia")));
        onView(withId(R.id.miep_silencios)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());

        //Prueba de HU-17 - Agregar alteración a una nota - Caso de no éxito
        onView(withId(R.id.miep_alteraciones)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.pee_lv_elementos)).atPosition(0).perform(click());
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Silencio Redonda")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp41_eliminarNotaEnPosicionSinNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Eliminar nota en posicion vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Eliminar nota en posicion vacia")));

        //Prueba de HU-18 - Eliminar una nota - Caso de no éxito
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
    public void cp42_reproducirPartituraSinNotas() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Reproducir partitura vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Reproducir partitura vacia")));

        //Prueba de HU-20 - Reproducir partitura - Caso de no éxito
        onView(withId(R.id.fl_imgbtn_reproducir)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Partitura vacía")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp43_pausarReproduccionNoIniciada() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Pausar reproduccion no iniciada"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Pausar reproduccion no iniciada")));
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_imgbtn_reproducir)).perform(click());

        //Prueba de HU-21 - Pausar reproducción - Caso de no éxito
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

        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Partitura vacía")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp44_reiniciarPartituraEnPrimeraNota() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Reiniciar partitura primer nota"));
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
        onView(withId(R.id.fl_tv_titulo)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Reiniciar partitura primer nota")));

        //Prueba de HU-22 - Reiniciar lectura de partitura - Caso de no éxito
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
    public void cp45_reiniciarPartituraSinNotas() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Reiniciar partitura vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Reiniciar partitura vacia")));

        //Prueba de HU-22 - Reiniciar lectura de partitura - Caso de no éxito
        onView(withId(R.id.fl_imgbtn_reiniciar)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Partitura vacía")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());

        scenario.close();
    }

    @Test
    public void cp46_desplazarseEnLaPartituraALaDerechaEnUltimaPosicion() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Desplazar derecha al final"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Desplazar derecha al final")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());

        //Prueba de HU-26 - Desplazarse en la partitura a la derecha - Caso de no éxito
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Agregar nota")));
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Desplazar derecha al final")));
        onView(withId(R.id.fl_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Do Negra")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        scenario.close();
    }

    @Test
    public void cp47_desplazarseEnLaPartituraSinNotasALaDerecha() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Desplazar derecha partitura vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Desplazar derecha partitura vacia")));

        //Prueba de HU-26 - Desplazarse en la partitura a la derecha - Caso de no éxito
        onView(withId(R.id.fe_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fe_tv_nota)).check(matches(withText("Agregar nota")));
        onView(withId(R.id.fe_imgbtn_modo_lectura)).perform(click());
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Desplazar derecha partitura vacia")));
        onView(withId(R.id.fl_imgbtn_desplazar_der)).perform(click());
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Partitura vacía")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        scenario.close();
    }

    @Test
    public void cp48_desplazarseEnLaPartituraALaIzquierdaEnPrimeraPosicion() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Desplazar izquierda al inicio"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Desplazar izquierda al inicio")));
        onView(withId(R.id.fe_imgbtn_añadir_nota)).perform(click());
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

        //Prueba de HU-27 - Desplazarse en la partitura a la izquierda - Caso de no éxito
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
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Desplazar izquierda al inicio")));
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
    public void cp49_desplazarseEnLaPartituraSinNotasALaIzquierda() {
        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);

        //Crear y verificar ambiente de prueba
        onView(withId(R.id.ah_btn_crear_partitura)).perform(click());
        onView(withId(R.id.pdp_et_nombre_partitura)).perform(typeText("Desplazar izquierda partitura vacia"));
        onView(withId(R.id.pdp_et_nombre_autor)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.pdp_btn_aceptar)).perform(click());
        onView(withId(R.id.fe_et_titulo)).check(matches(withText("Desplazar izquierda partitura vacia")));

        //Prueba de HU-27 - Desplazarse en la partitura a la izquierda - Caso de no éxito
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
        onView(withId(R.id.fl_tv_titulo)).check(matches(withText("Desplazar izquierda partitura vacia")));
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
        onView(withId(R.id.fl_tv_nota)).check(matches(withText("Partitura vacía")));

        //Limpiar ambiente de pruebas
        onView(withId(R.id.mt_inicio)).perform(click());
        onView(withId(R.id.ah_tv_partituras_anteriores)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ah_lv_partituras)).atPosition(0).perform(click());
        onView(withId(R.id.pop_imgbtn_eliminar_partitura)).perform(click());
        scenario.close();
    }
}