import {html, PolymerElement} from '@polymer/polymer';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class ChangeEmailView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout style="margin: var(--lumo-space-xl); align-self: center; align-items: center;">
  <vaadin-text-field label="Old email:" id="oldEmail"></vaadin-text-field>
  <vaadin-text-field label="New email:" id="newEmail"></vaadin-text-field>
  <vaadin-text-field label="Confirm new email:" id="confirmNewEmail"></vaadin-text-field>
  <vaadin-button theme="primary success" id="changeButton">
   Change 
  </vaadin-button>
  <vaadin-button theme="primary error" id="cancelButton">
   Cancel 
  </vaadin-button>
 </vaadin-vertical-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'change-email-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ChangeEmailView.is, ChangeEmailView);
