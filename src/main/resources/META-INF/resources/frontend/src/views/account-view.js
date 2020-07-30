import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '../components/menu-component.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class AccountView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-horizontal-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout class="sidebar" style="flex-basis: calc(7*var(--lumo-size-s)); flex-shrink: 0; background-color: var(--lumo-contrast-5pct);">
  <menu-component id="menuComponent" style="flex-shrink: 0; align-self: stretch;"></menu-component>
 </vaadin-vertical-layout>
 <vaadin-vertical-layout class="content" style="flex-grow: 1; flex-shrink: 1; flex-basis: auto; justify-content: flex-start; background-color: var(--lumo-shade-20pct);" id="vaadinVerticalLayout">
  <vaadin-vertical-layout style="align-self: center; margin: var(--lumo-space-xl);">
   <vaadin-button theme="primary" id="changeEmailButton" style="align-self: stretch;">
     Change email 
   </vaadin-button>
   <vaadin-button theme="primary" id="changePasswordButton" style="align-self: stretch;">
     Change password 
   </vaadin-button>
  </vaadin-vertical-layout>
 </vaadin-vertical-layout>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'account-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(AccountView.is, AccountView);
