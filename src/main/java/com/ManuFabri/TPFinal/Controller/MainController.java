package com.ManuFabri.TPFinal.Controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ManuFabri.TPFinal.Model.Cliente;
import com.ManuFabri.TPFinal.Model.ClientesDAO;
import com.ManuFabri.TPFinal.Model.Mecanico;
import com.ManuFabri.TPFinal.Model.MecanicosDAO;
import com.ManuFabri.TPFinal.Model.Orden;
import com.ManuFabri.TPFinal.Model.OrdenRepuesto;
import com.ManuFabri.TPFinal.Model.OrdenesDAO;
import com.ManuFabri.TPFinal.Model.OrdenesRepuestosDAO;
import com.ManuFabri.TPFinal.Model.Repuesto;
import com.ManuFabri.TPFinal.Model.RepuestosDAO;
import com.ManuFabri.TPFinal.Model.Usuario;
import com.ManuFabri.TPFinal.Model.UsuariosDAO;

@Controller

@SessionAttributes("usuarioLogueado")
public class MainController {
	
	
	@Autowired
	private ClientesDAO clientesDAO;
	@Autowired
	private MecanicosDAO mecanicosDAO;
	@Autowired
	private OrdenesDAO ordenesDAO;
	@Autowired
	private OrdenesRepuestosDAO ordenesRepuestosDAO;
	@Autowired
	private RepuestosDAO repuestosDAO;
	@Autowired
	private UsuariosDAO usuariosDAO;
	
	
	//LOGIN
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView loginGET(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		//SI NO SE LOGUEO EL USUARIO, MUESTRO LOGIN 
		if(!model.containsAttribute("usuarioLogueado")) {
			model.addAttribute("usuario", new Usuario());
			modelAndView.addObject("usuario", new Usuario());
			modelAndView.setViewName("login");		
			return modelAndView;
		}
		modelAndView.setViewName("home");		
		return modelAndView;
	}
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView loginPOST(Model model, @ModelAttribute Usuario usuario) {
		boolean found = false;
		ModelAndView modelAndView = new ModelAndView();
		//VALIDO USUARIO CARGADO
		for(Usuario u: usuariosDAO.findAll()) {
			//VALIDO USER NAME
			if(u.getUser().equals(usuario.getUser())) {
				// SI LO ENCUENTRA, VALIDO PASSWORD
				if(u.getPassword().equals(usuario.getPassword())) {
					//SI ESTAN BIEN, SETEO FOUND EN TRUE Y SALGO
					
					model.addAttribute("usuarioLogueado", u);
					found = true;
					break;
				}
			}
		}
		//SI ENCONTRO AGREGO EL USUARIO AL MODEL Y VOY AL HOME
		if(found) {
			modelAndView.setViewName("home");		
			return modelAndView;
		}else{
			//VUELVO AL LOGIN
			modelAndView.addObject("usuario", new Usuario());
			modelAndView.setViewName("login");		
			return modelAndView;
		}
		
	}
	//---------------------------
	//HOME
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView homeGET(Model model) {
		//VALIDO SI ESTA LOGUEADO EL USUARIO 
		if(model.containsAttribute("usuarioLogueado")) {
			//SI ESTA, MANDO A HOME
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("home");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	//---------------------------
	//REPUESTOS
	//VER REPUESTOS
	@RequestMapping(value="/verRepuestos", method = RequestMethod.GET)
	public ModelAndView verRepuestosGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView(); 
			modelAndView.addObject("repuestos", repuestosDAO.findAll());
			modelAndView.setViewName("verRepuestos");		
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
		
	}
	//---------------------------
	
	//CLIENTES
	//CARGAR CLIENTE
	@RequestMapping(value="/cargarCliente", method = RequestMethod.GET)
	public ModelAndView cargarClienteGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("cliente", new Cliente());
			modelAndView.setViewName("cargarCliente");		
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	@RequestMapping(value="/cargarCliente", method = RequestMethod.POST)
	public ModelAndView cargarClientePost(@ModelAttribute Cliente cliente) {
		//SALVO EL CLIENTE DEL FORMULARIO
		clientesDAO.save(cliente);
		ModelAndView modelAndView = new ModelAndView();
		//CARGO LA LISTA DE CLIENTES A LA VISTA
		modelAndView.addObject("clientes", clientesDAO.findAll());
		modelAndView.setViewName("verClientes");		
		return modelAndView;
	}
	//VER CLIENTE
	@RequestMapping(value="/verClientes", method = RequestMethod.GET)
	public ModelAndView verClientesGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView(); 
			//CARGO LISTA DE CLIENTES A LA VISTA
			modelAndView.addObject("clientes", clientesDAO.findAll());
			modelAndView.setViewName("verClientes");		
			return modelAndView;
			
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	//BORRAR CLIENTE
	@RequestMapping(value="/borrarC", method = RequestMethod.GET)
	public ModelAndView borrarClienteGET(@RequestParam("id") long idCliente, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO PARA EVITAR UN BORRADO X URL
		if(model.containsAttribute("usuarioLogueado")) {
			Cliente cli = clientesDAO.findOne(idCliente);
			//PARA CADA ORDEN ABIERTA POR EL CLIENTE
			for(int i = 0; i<cli.getListaDeOrdenes().size();i++) {
				//PARA CADA REPUESTO DE LA ORDEN DEL CLIENTE
				for(int j = 0; j < cli.getListaDeOrdenes().get(i).getListaOrdenesRepuestosO().size(); j++) {
					//BORRO EL REPUESTO USADO
					ordenesRepuestosDAO.delete(cli.getListaDeOrdenes().get(i).getListaOrdenesRepuestosO().get(j));
				}
				//BORRO LA ORDEN
				ordenesDAO.delete(cli.getListaDeOrdenes().get(i));
			}
			//BORRO EL CLIENTE
			clientesDAO.delete(cli);
			ModelAndView modelAndView = new ModelAndView(); 
			//CARGO LISTA DE CLIENTES A LA VISTA
			modelAndView.addObject("clientes", clientesDAO.findAll());
			modelAndView.setViewName("verClientes");		
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	//---------------------------
	
	//MECANICOS
	//CARGAR MECANICO
	@RequestMapping(value="/cargarMecanico", method = RequestMethod.GET)
	public ModelAndView cargarMecanicoGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("mecanico", new Mecanico());
			modelAndView.setViewName("cargarMecanico");		
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	@RequestMapping(value="/cargarMecanico", method = RequestMethod.POST)
	public ModelAndView cargarMecanicoPost(@ModelAttribute Mecanico mecanico) {
		//SALVO EL MECANICO DEL FORMULARIO
		mecanicosDAO.save(mecanico);
		ModelAndView modelAndView = new ModelAndView();
		//CARGO LA LISTA DE CLIENTES A LA VISTA
		modelAndView.addObject("mecanicos", mecanicosDAO.findAll());
		modelAndView.setViewName("verMecanicos");		
		return modelAndView;
	}
	//VER MECANICOS
	@RequestMapping(value="/verMecanicos", method = RequestMethod.GET)
	public ModelAndView verMecanicoGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView();
			//CARGO LA LISTA DE MECANICOS A LA VISTA
			modelAndView.addObject("mecanicos", mecanicosDAO.findAll());
			modelAndView.setViewName("verMecanicos");		
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	//BORRAR MECANICO
	@RequestMapping(value="/borrarM", method = RequestMethod.GET)
	public ModelAndView borrarMecanicoGET(@RequestParam("id") long idMecanico, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			Mecanico meca = mecanicosDAO.findOne(idMecanico);
			//PARA CADA ORDEN A CARGO DEL MECANICO
			for(int i = 0; i<meca.getListaDeOrdenes().size();i++) {
				//PARA CADA REPUESTO DE LA ORDEN A CARGO DEL MECANICO
				for(int j = 0; j < meca.getListaDeOrdenes().get(i).getListaOrdenesRepuestosO().size(); j++) {
					//BORRO EL REPUESTO USADO
					ordenesRepuestosDAO.delete(meca.getListaDeOrdenes().get(i).getListaOrdenesRepuestosO().get(j));
				}
				//BORRO LA ORDEN
				ordenesDAO.delete(meca.getListaDeOrdenes().get(i));
			}
			//BORRO EL MECANICO
			mecanicosDAO.delete(meca);
			ModelAndView modelAndView = new ModelAndView();
			//CARGO LA LISTA DE MECANICOS A LA VISTA
			modelAndView.addObject("mecanicos", mecanicosDAO.findAll());
			modelAndView.setViewName("verMecanicos");		
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	//---------------------------
	
	
	//ORDENES
	//NUEVA ORDEN
	@RequestMapping(value="/nuevaOrden", method=RequestMethod.GET)
	public ModelAndView nuevaOrdenGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("mecanicos", mecanicosDAO.findAll());
			modelAndView.addObject("clientes", clientesDAO.findAll());
			modelAndView.addObject("orden", new Orden());
			modelAndView.setViewName("nuevaOrden");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	//NUEVA ORDEN DESDE CLIENTE
	@RequestMapping(value="/cargarOrden", method=RequestMethod.GET)
	public ModelAndView nuevaOrdenGET(@RequestParam("id") long idCliente, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("mecanicos", mecanicosDAO.findAll());
			modelAndView.addObject("cliente", clientesDAO.findOne(idCliente));
			modelAndView.addObject("orden", new Orden());
			modelAndView.setViewName("cargarOrden");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	@RequestMapping(value="/nuevaOrden", method=RequestMethod.POST)
	public ModelAndView nuevaOrdenPOST(@ModelAttribute Mecanico mecanico, 
									   @ModelAttribute Cliente cliente,
									   @ModelAttribute Orden orden) {
		//LE SETEO FECHA DE HOY, LA SETEO ABIERTA Y GUARDO LA ORDEN
		orden.setfIngreso(Calendar.getInstance().getTime().toString());
		orden.setOrdenAbierta(true);
		ordenesDAO.save(orden);
		//CARGO LAS ORDENES Y LAS MUESTRO
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("ordenes", ordenesDAO.findAll());
		modelAndView.setViewName("verOrdenes");
		return modelAndView;
	}
	//CARGAR REPUESTOS A UNA ORDEN
	@RequestMapping(value="/cargarRepuestos", method=RequestMethod.GET)
	public ModelAndView cargarRepuestosGET(@RequestParam("id") long idOrden, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			Orden orden = ordenesDAO.findOne(idOrden);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("orden", orden);
			modelAndView.addObject("repuestos", repuestosDAO.findAll());
			modelAndView.addObject("ordenRepuesto", new OrdenRepuesto());
			modelAndView.setViewName("cargarRepuestosO");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}	
	}
	@RequestMapping(value="/cargarRepuestosO", method=RequestMethod.POST)
	public ModelAndView cargarRepuestosPOST(@ModelAttribute Orden orden,
											@ModelAttribute Repuesto repuesto,
											@ModelAttribute OrdenRepuesto ordenRepuesto) {
		ordenRepuesto.setOrden(orden);
		ordenRepuesto.setRepuesto(repuesto);
		ordenesRepuestosDAO.save(ordenRepuesto);
		
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("orden", ordenesDAO.findOne(orden.getIdOrden()));
		modelAndView.addObject("ordenesRepuestos", ordenesDAO.findOne(orden.getIdOrden()).getListaOrdenesRepuestosO());
		modelAndView.setViewName("verDetalles");
		return modelAndView;
	}	
	//VER ORDENES
	@RequestMapping(value="/verOrdenes", method=RequestMethod.GET)
	public ModelAndView verOrdenesGET(Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			//CARGO LAS ORDENES Y LAS MUESTRO
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("ordenes", ordenesDAO.findAll());
			modelAndView.setViewName("verOrdenes");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}	
	}
	//VER ORDENES DESDE CLIENTE
	@RequestMapping(value="/verOrdenesCli", method=RequestMethod.GET)
	public ModelAndView verOrdenesCliGET(@RequestParam("id") long idCliente, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			Cliente cli = clientesDAO.findOne(idCliente);
			//CARGO LAS ORDENES Y LAS MUESTRO
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("ordenes", cli.getListaDeOrdenes());
			modelAndView.setViewName("verOrdenes");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}	
	}
	//VER ORDENES DESDE MECANICO
	@RequestMapping(value="/verOrdenesMec", method=RequestMethod.GET)
	public ModelAndView verOrdenesMecaGET(@RequestParam("id") long idMecanico, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			Mecanico mec = mecanicosDAO.findOne(idMecanico);
			//CARGO LAS ORDENES Y LAS MUESTRO
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("ordenes", mec.getListaDeOrdenes());
			modelAndView.setViewName("verOrdenes");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}	
	}
	//CERRAR ORDEN 4
	@RequestMapping(value="/cerrarOrden", method=RequestMethod.GET)
	public ModelAndView cerrarOrden(@ModelAttribute("usuarioLogueado") Usuario usuario, @RequestParam("id") long idOrden) {
		//CIERRO Y GUARDO LA ORDEN
		Orden orden = ordenesDAO.findOne(idOrden);
		orden.setOrdenAbierta(false);
		orden.setUsuario(usuario);
		ordenesDAO.save(orden);
		//CARGO LOS DETALLES Y LOS MUESTRO
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("ordenes", ordenesDAO.findAll());
		modelAndView.setViewName("verOrdenes");
		return modelAndView;
	}
	
	//VER DETALLES DE UNA ORDEN
	@RequestMapping(value="/verDetalles", method = RequestMethod.GET)
	public ModelAndView verDetalles(@RequestParam("id") long idOrden, @ModelAttribute Repuesto repuestoHTML, @ModelAttribute Orden ordenHTML, @ModelAttribute OrdenRepuesto ordenRepuestoHTML, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			Orden orden = ordenesDAO.findOne(idOrden);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("orden", orden);
			modelAndView.addObject("ordenesRepuestos", orden.getListaOrdenesRepuestosO());
			modelAndView.setViewName("verDetalles");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}	
	}
	
	//BORRAR ORDEN
	@RequestMapping(value="/eliminarOrden", method=RequestMethod.GET)
	public ModelAndView eliminarOrdenGET(@RequestParam("id") long idOrden, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			Orden orden = ordenesDAO.findOne(idOrden);
			//PARA CADA REPUESTO EN LA ORDEN
			for(int i = 0; i<orden.getListaOrdenesRepuestosO().size(); i++) {
				//BORRO EL REPUESTO
				ordenesRepuestosDAO.delete(orden.getListaOrdenesRepuestosO().get(i));
			}
			//BORRO LA ORDEN
			ordenesDAO.delete(orden);
			//CARGO LAS ORDENES Y LAS MUESTRO
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("ordenes", ordenesDAO.findAll());
			modelAndView.setViewName("verOrdenes");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	

	//VER DETALLES REPUESTO
	@RequestMapping(value="/detalleRepuesto", method=RequestMethod.GET)
	public ModelAndView detalleRepuestoGET(@RequestParam("idR") long idR, @RequestParam("idO") long idO, Model model) {
		//VALIDO SI EL USUARIO SE LOGUEO
		if(model.containsAttribute("usuarioLogueado")) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("repuesto", repuestosDAO.findOne(idR));
			modelAndView.addObject("orden", ordenesDAO.findOne(idO));
			modelAndView.setViewName("verDetalleRepuesto");
			return modelAndView;
		}else {
			//SINO, DEVUELVO AL LOGIN
			return new ModelAndView("redirect:/");	
		}
	}
	
	//---------------------------
	
}
