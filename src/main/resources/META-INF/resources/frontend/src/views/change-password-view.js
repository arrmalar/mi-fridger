import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-password-field.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class ChangePasswordView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%; align-items: center;">
 <vaadin-vertical-layout style="align-self: center; margin: var(--lumo-space-xl);">
  <vaadin-password-field label="Old password:" value="secret1" id="oldPassword"></vaadin-password-field>
  <vaadin-password-field label="New password:" value="secret1" id="newPassword"></vaadin-password-field>
  <vaadin-password-field label="Confirm new password:" value="secret1" id="newPasswordConfirm"></vaadin-password-field>
  <vaadin-button theme="primary success" id="changeButton" style="align-self: center;">
    Change 
  </vaadin-button>
  <vaadin-button theme="primary error" id="cancelButton" style="align-self: center;">
    Cancel 
  </vaadin-button>
 </vaadin-vertical-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'change-password-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ChangePasswordView.is, ChangePasswordView);
