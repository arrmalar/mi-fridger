package com.meefri.app.security;

import com.meefri.app.repositories.UserRepository;
import com.meefri.app.ui.views.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.meefri.app.ui.components.LoginComponent;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	private UserRepository userRepository;

	ConfigureUIServiceInitListener(@Autowired UserRepository userRepository){
		this.userRepository = userRepository;
	}

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/**
	 * Reroutes the user if (s)he is not authorized to access the view.
	 *
	 * @param event
	 *            before navigation event with event details
	 */

	private void beforeEnter(BeforeEnterEvent event) {
		if(SecurityUtils.isUserLoggedIn()){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {

				 	 if(MainViewNotLogged.class.equals(event.getNavigationTarget())) {
				 	 	event.rerouteTo(MainView.class);
					 	return;
				 	 }
					 event.rerouteTo(String.valueOf(event.getNavigationTarget()));
					 return;

			}
		}

		if ( !MainViewNotLogged.class.equals( event.getNavigationTarget() )  ) {
			event.rerouteTo(MainViewNotLogged.class);
		}
	}
}