import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-text-field/src/vaadin-password-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-email-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';

class RegistrationView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%; align-items: center;">
 <vaadin-text-field id="usernameTexField" style="margin: var(--lumo-space-xs);" label="Username"></vaadin-text-field>
 <vaadin-password-field label="Password" value="secret1" id="password"></vaadin-password-field>
 <vaadin-password-field label="Confirm password" value="secret1" id="confirmPassword"></vaadin-password-field>
 <vaadin-email-field id="email" label="Email"></vaadin-email-field>
 <vaadin-email-field id="confirmEmail" label="Confirm email"></vaadin-email-field>
 <vaadin-button theme="primary" style="align-self: center;" id="signUpButton">
   Sign up 
 </vaadin-button>
 <vaadin-button theme="primary error" id="cancelButton">
  Cancel
 </vaadin-button>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'registration-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(RegistrationView.is, RegistrationView);
