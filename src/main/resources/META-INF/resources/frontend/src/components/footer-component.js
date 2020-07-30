import {html, PolymerElement} from '@polymer/polymer';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class FooterComponent extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%;">
 <vaadin-horizontal-layout id="footerHorizontalLayout" style="width: 100%; justify-content: space-between; background-color: hsl(75, 60%, 60%);">
  <vaadin-button theme="" id="compareButton" style="background-color: hsl(216, 100%, 40%); color: white;">
    Compare with my fridge 
  </vaadin-button>
  <vaadin-button style="background-color: hsl(216, 100%, 40%); color: white;">
   Show recipe 
  </vaadin-button>
  <vaadin-button id="addFavouriteButton" style="background-color: hsl(216, 100%, 40%); color: white;">
    Add to favourite 
  </vaadin-button>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'footer-component';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(FooterComponent.is, FooterComponent);
