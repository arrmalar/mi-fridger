import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-upload/src/vaadin-upload.js';

class AdminPanel extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-text-field label="Label" placeholder="Placeholder"></vaadin-text-field>
 <vaadin-text-area label="Write a description" placeholder="Add detailed explanation"></vaadin-text-area>
 <vaadin-text-field label="Label" placeholder="Placeholder" id="label"></vaadin-text-field>
 <vaadin-upload></vaadin-upload>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'admin-panel';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(AdminPanel.is, AdminPanel);
