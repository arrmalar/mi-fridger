import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

class DialogLayoutComponent extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout id="dialogLayoutComponent" style="width: 100%; height: 100%; flex-grow: 1;"></vaadin-vertical-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'dialog-layout-component';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(DialogLayoutComponent.is, DialogLayoutComponent);
