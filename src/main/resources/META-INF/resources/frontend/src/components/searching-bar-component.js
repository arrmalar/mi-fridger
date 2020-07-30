import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class SearchingBarComponent extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout id="vaadinVerticalLayout" style="align-items: stretch; align-self: stretch;">
  <vaadin-combo-box id="comboBox" style="flex-shrink: 0; flex-grow: 0; align-self: stretch; width: 100%;" label="Select ingredient:" clear-button-visible>
   <iron-icon icon="lumo:search" slot="prefix"></iron-icon>
  </vaadin-combo-box>
 </vaadin-vertical-layout>
 <vaadin-button theme="primary error" id="clearButton" style="align-self: center;">
  Clear 
 </vaadin-button>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'searching-bar-component';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(SearchingBarComponent.is, SearchingBarComponent);
