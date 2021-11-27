package mx.madg.easyfit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import mx.madg.easyfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var tipo : Int = intent.getIntExtra("tipo",0)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        if(tipo==0){
            var menu:Menu = navView.menu
            var visible : MenuItem = menu.findItem(R.id.nav_contactos)
            var perfil : MenuItem = menu.findItem(R.id.nav_perfil_nutriologo)
            var perfilE : MenuItem = menu.findItem(R.id.nav_perfil_entrenador)
            visible.setVisible(false)
            perfil.setVisible(false)
            perfilE.setVisible(false)
        }else if(tipo == 1){
            var menu:Menu = navView.menu
            var visible : MenuItem = menu.findItem(R.id.nav_rutinas)
            var perfil : MenuItem = menu.findItem(R.id.nav_perfil)
            var perfilE : MenuItem = menu.findItem(R.id.nav_perfil_entrenador)
            var dieta : MenuItem = menu.findItem(R.id.nav_dietas)
            dieta.setVisible(false)
            visible.setVisible(false)
            perfil.setVisible(false)
            perfilE.setVisible(false)

        }else if(tipo==2){
            var menu:Menu = navView.menu
            var visible : MenuItem = menu.findItem(R.id.nav_dietas)
            var perfil : MenuItem = menu.findItem(R.id.nav_perfil)
            var contact : MenuItem = menu.findItem(R.id.nav_contactos)
            var perfilE : MenuItem = menu.findItem(R.id.nav_perfil_nutriologo)
            var dieta : MenuItem = menu.findItem(R.id.nav_rutinas)
            dieta.setVisible(false)
            contact.setVisible(false)
            visible.setVisible(false)
            perfil.setVisible(false)
            perfilE.setVisible(false)


        }



        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_perfil, R.id.nav_contactos,
                R.id.nav_calendario, R.id.nav_dietas, R.id.nav_rutinas
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}