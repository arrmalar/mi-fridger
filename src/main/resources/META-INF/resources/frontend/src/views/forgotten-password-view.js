import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';

class ForgottenPasswordView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%; padding: var(--lumo-space-xl);">
 <vaadin-vertical-layout style="align-self: center; align-items: stretch;">
  <vaadin-text-field label="Enter your email:" placeholder="Email" id="emailTextField"></vaadin-text-field>
 </vaadin-vertical-layout>
 <vaadin-vertical-layout style="align-self: center; align-items: stretch;">
  <vaadin-button theme="primary success" id="sendPasswordButton">
    Send password 
  </vaadin-button>
  <vaadin-button theme="primary error" id="cancelButton">
    Cancel 
  </vaadin-button>
 </vaadin-vertical-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'forgotten-password-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ForgottenPasswordView.is, ForgottenPasswordView);
